// Obtener la referencia a la base de datos 'tripdb'
db = db.getSiblingDB('tripdb');

// Insertar datos en la colección 'trips'
db.trips.insertMany([
    {
        accountId: "1",
        startDateTime: new Date("2024-11-01T10:00:00"),
        endDateTime: new Date("2024-11-01T12:00:00"),
        distanceTraveled: 15.5,
        duration: 120,
        creditsConsumed: 5.0,
        scooterId: 1,
        pausesId: ["pause1", "pause2"]
    },
    {
        accountId: "2",
        startDateTime: new Date("2024-11-02T14:00:00"),
        endDateTime: new Date("2024-11-02T15:30:00"),
        distanceTraveled: 10.0,
        duration: 90,
        creditsConsumed: 4.0,
        scooterId: 2,
        pausesId: ["pause3"]
    },
    {
        accountId: "3",
        startDateTime: new Date("2024-11-03T16:00:00"),
        endDateTime: new Date("2024-11-03T18:00:00"),
        distanceTraveled: 20.0,
        duration: 120,
        creditsConsumed: 6.5,
        scooterId: 3,
        pausesId: ["pause4", "pause5"]
    }
]);

// Insertar datos en la colección 'pauses' (si es necesario)
db.pauses.insertMany([
    { pauseId: "pause1", pauseStart: new Date("2024-11-01T10:30:00"), pauseEnd: new Date("2024-11-01T10:45:00") },
    { pauseId: "pause2", pauseStart: new Date("2024-11-01T11:00:00"), pauseEnd: new Date("2024-11-01T11:15:00") },
    { pauseId: "pause3", pauseStart: new Date("2024-11-02T14:30:00"), pauseEnd: new Date("2024-11-02T14:40:00") },
    { pauseId: "pause4", pauseStart: new Date("2024-11-03T16:30:00"), pauseEnd: new Date("2024-11-03T16:45:00") },
    { pauseId: "pause5", pauseStart: new Date("2024-11-03T17:00:00"), pauseEnd: new Date("2024-11-03T17:15:00") }
]);
