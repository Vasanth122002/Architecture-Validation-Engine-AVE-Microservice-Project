const mongoose = require("mongoose");

const serviceHealthSchema = new mongoose.Schema({
  serviceId: { type: String, required: true },
  status: { type: String, enum: ["OK", "DEGRADED", "OUTAGE"], required: true },
  latencyMs: { type: Number, default: 0 },
  timestamp: { type: Date, default: Date.now },
});

serviceHealthSchema.index({ serviceId: 1, timestamp: -1 });

module.exports = mongoose.model("ServiceHealth", serviceHealthSchema);
