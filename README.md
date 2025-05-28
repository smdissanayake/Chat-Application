<h1 align="center">💬 Smart Chat Application</h1>

<p align="center">
  A mobile-friendly chat app built using <strong>React Native</strong> with a powerful <strong>Java EE backend</strong> and <strong>MySQL</strong> database 💻📱<br>
  Features real-time messaging and a modern story system like WhatsApp or Instagram! 📸
</p>

<p align="center">
  <img src="https://img.shields.io/badge/React--Native-Mobile%20UI-blue?style=flat&logo=react" />
  <img src="https://img.shields.io/badge/Java%20EE-Backend-orange?style=flat&logo=java" />
  <img src="https://img.shields.io/badge/MySQL-Database-00758F?style=flat&logo=mysql" />
  <img src="https://img.shields.io/badge/Status-In%20Development-yellow" />
</p>

---

## ✨ Features

- 💬 **1-on-1 Chat** – Real-time message exchange
- 🧑‍🤝‍🧑 **Clean UI** – Optimized for mobile screens
- 📸 **Story Feature** – Users can post short stories with images/text
- 🕒 **Timestamps** – Track when messages are sent and seen
- 🔐 **User Authentication** – Login & register via Java EE backend
- 🗄️ **MySQL DB Integration** – Stores user data, messages, and stories

---

## 🔧 Tech Stack

| Layer         | Technology            |
|---------------|------------------------|
| 📱 Frontend   | React Native           |
| 💻 Backend    | Java EE (Servlets + REST APIs) |
| 🛢️ Database  | MySQL                  |
| 🌐 API Comm. | HTTP + JSON            |
| 🚀 Tools      | Expo / Android Studio (for mobile), GlassFish (or Tomcat) for backend |

---

## 📲 Screenshots / Demos

> *(Add demo GIFs or screenshots here if available)*

---

## 🛠️ Installation

### Backend (Java EE)
1. Clone the backend project and open in NetBeans or IntelliJ
2. Setup MySQL database and update DB config in `persistence.xml` or JDBC file
3. Deploy on GlassFish/Tomcat
4. Start the server

### Frontend (React Native)
```bash
git clone https://github.com/your-username/smart-chat-app.git
cd smart-chat-app
npm install
npx expo start
```

### 📁 Project Structure
```bash
📦 smart-chat-app
 ┣ 📁assets
 ┣ 📁components
 ┣ 📁screens
 ┣ 📁services
 ┗ 📄App.js

📦 smart-chat-backend
 ┣ 📁src
 ┃ ┣ 📁servlets
 ┃ ┣ 📁models
 ┃ ┣ 📁utils
 ┃ ┗ 📄persistence.xml
 ┗ 📄web.xml

