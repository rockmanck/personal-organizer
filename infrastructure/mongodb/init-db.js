// MongoDB initialization script for Jedi Organizer
// This script sets up the initial database structure and indexes

// Switch to the jedi_organizer database
db = db.getSiblingDB('jedi_organizer');

// Create collections
db.createCollection('users');
db.createCollection('tasks');
db.createCollection('projects');
db.createCollection('reflection_sessions');

// Create indexes for optimal performance

// Users collection indexes
db.users.createIndex({ "googleId": 1 }, { unique: true });
db.users.createIndex({ "email": 1 }, { unique: true });

// Tasks collection indexes
db.tasks.createIndex({ "userId": 1, "state": 1 });
db.tasks.createIndex({ "userId": 1, "userDefinedOrder": 1 });
db.tasks.createIndex({ "userId": 1, "completedAt": -1 });
db.tasks.createIndex({ "userId": 1, "createdAt": -1 });

// Projects collection indexes
db.projects.createIndex({ "userId": 1, "createdAt": -1 });
db.projects.createIndex({ "userId": 1, "completedAt": -1 });

// Reflection sessions collection indexes
db.reflection_sessions.createIndex({ "userId": 1, "sessionDate": -1 });

print("Database initialization completed successfully");
