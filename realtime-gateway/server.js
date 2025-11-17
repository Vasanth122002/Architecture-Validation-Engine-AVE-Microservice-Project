require("dotenv").config();
const express = require("express");
const http = require("http");
const socketIo = require("socket.io");
const axios = require("axios");
const mongoose = require("mongoose");
const cors = require("cors");

const ServiceHealth = require("./models/ServiceHealth");

const app = express();
const server = http.createServer(app);

app.use(
  cors({
    origin: "http://localhost:3000",
    credentials: true,
  })
);
app.use(express.json());

const io = socketIo(server, { cors: { origin: "http://localhost:3000" } });

mongoose
  .connect(process.env.MONGODB_URI)
  .then(() => console.log("MongoDB connected successfully."))
  .catch((err) => console.error("MongoDB connection error:", err));

io.on("connection", (socket) => {
  console.log("Client connected for AVE real-time alerts.");
});

app.post("/api/v1/health-ingest", async (req, res) => {
  try {
    const { serviceId, status, latencyMs } = req.body;
    const healthRecord = new ServiceHealth({ serviceId, status, latencyMs });
    await healthRecord.save();

    io.emit("healthUpdate", healthRecord);

    if (status === "OUTAGE") {
      io.emit("outageAlert", {
        serviceId,
        message: `CRITICAL: Service ${serviceId} is down!`,
      });
    }

    res.status(201).send({ message: "Health data ingested." });
  } catch (error) {
    res.status(500).send({ error: error.message });
  }
});

app.get("/api/v1/predict-outage/:serviceId", async (req, res) => {
  const { serviceId } = req.params;

  try {
    const response = await axios.get(
      `${process.env.SPRING_BOOT_URL}/api/v1/impact/predict/${serviceId}`
    );

    res.json({
      failedServiceId: serviceId,
      predictedImpact: response.data,
    });
  } catch (error) {
    console.error(
      "Error calling Spring Boot Prediction Service:",
      error.message
    );
    res
      .status(500)
      .send({ error: "Prediction failed: Could not access core registry." });
  }
});

const PORT = process.env.PORT || 4000;
server.listen(PORT, () =>
  console.log(`Node.js Health Service running on port ${PORT}`)
);
