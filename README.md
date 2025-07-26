# 🃏 Multiplayer Poker Game

A fully-functional multiplayer poker game developed in Java with a Swing-based GUI and real-time server-client communication. Designed with object-oriented principles, robust testing, and clean architecture.

## 🚀 Features

- 🎮 **Multiplayer Support** – Real-time interaction between multiple players and a dealer using client-server architecture.
- 🖥️ **Graphical User Interface** – Built using Java Swing for an intuitive poker table experience.
- ⚙️ **Real-Time Communication** – Socket programming with multithreading to handle concurrent player connections.
- 🔄 **JSON Data Handling** – Utilizes Google Gson for fast and flexible data serialization/deserialization.
- 🧪 **Robust Testing** – Unit tests implemented using JUnit 5 and Mockito for high test coverage and reliability.

## 🛠️ Technologies Used

- **Language:** Java  
- **GUI:** Java Swing  
- **Networking:** Socket Programming, Multi-threading  
- **Data Format:** JSON  
- **Libraries:** 
  - [Google Gson](https://github.com/google/gson) – for JSON handling  
  - [JUnit 5](https://junit.org/junit5/) – for unit testing  
  - [Mockito](https://site.mockito.org/) – for mocking dependencies  
- **Version Control:** Git

## 🧪 Testing Strategy

- Created isolated unit tests using **JUnit 5**.
- Utilized **Mockito** to mock dependencies such as:
  - Screen dimensions
  - Network interfaces
  - IP addresses
- Ensured components can be tested independently of external systems.

## 🖼️ Screenshots

> *(Insert screenshots of your poker table UI here if available)*

## 🔧 Getting Started

### Prerequisites
- Java 8 or higher
- IntelliJ IDEA (or any Java IDE)
- Git (optional, for version control)

### Clone the Repository
```bash
git clone https://github.com/JJTecca/MultiplayerPoker.git
cd MultiplayerPoker
