const mongoose = require("mongoose");

const localURI = "mongodb://127.0.0.1:27017/myDatabase";

async function connectDB() {
  try {
    const connectionURI = localURI;

    const conn = await mongoose.connect(connectionURI);

    console.log(`✅ MongoDB Connected: ${conn.connection.host}`);
  } catch (error) {
    console.error(`❌ MongoDB Connection Error: ${error.message}`);

    process.exit(1);
  }
}

module.exports = connectDB;
