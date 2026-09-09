<div align="center">

# 🏨 Hotel Management System

### A robust desktop application for end-to-end hotel administration — built in Java Swing

[![Java](https://img.shields.io/badge/Java-8%2B-orange?style=for-the-badge&logo=openjdk&logoColor=white)](https://www.oracle.com/java/)
[![Swing](https://img.shields.io/badge/UI-Java%20Swing-blue?style=for-the-badge&logo=java&logoColor=white)](https://docs.oracle.com/javase/tutorial/uiswing/)
[![License](https://img.shields.io/badge/License-MIT-green?style=for-the-badge)](LICENSE)
[![PRs Welcome](https://img.shields.io/badge/PRs-Welcome-brightgreen?style=for-the-badge)](CONTRIBUTING.md)

**[Overview](#-overview)** · **[Features](#-features)** · **[Architecture](#️-architecture--design-patterns)** · **[Getting Started](#-getting-started)** · **[Usage](#-usage-guide)** · **[Roadmap](#-roadmap)**

</div>

<br>

## 📌 Overview

**Hotel Management System** is a full-featured desktop application that streamlines day-to-day hotel operations — from front-desk check-ins to back-office reporting. It provides a clean, panel-based interface for managing **employees, rooms, drivers, customers, and reservations**, and it ships with a working demonstration of the **Adapter design pattern** for exporting operational data to CSV and JSON.

Built as both a functional tool and a showcase of solid OOP design, the project pairs a practical Swing UI with a small but deliberate set of design patterns (Singleton, Adapter, Observer) that keep the codebase maintainable and easy to extend.

<br>

## ✨ Features

<table>
<tr>
<td width="50%" valign="top">

### 👥 Employee Management
- Add, edit, and delete employee records
- Assign departments (Reception, Housekeeping, Kitchen, etc.)
- Track salaries and personal details
- View employee statistics at a glance

### 🛏️ Room Management
- Room inventory by category (Single, Double, Suite, Deluxe)
- Live availability and cleanliness status
- Dynamic per-room pricing
- Real-time status updates on check-in/out

### 🚗 Driver Management
- Driver profiles with car and company details
- Availability and location tracking

</td>
<td width="50%" valign="top">

### 👤 Customer Management
- Full CRUD for customer records
- Room assignment on check-in
- Check-in / check-out date tracking
- Duplicate-entry prevention

### 📅 Reservation Management
- Create and manage reservations
- Status pipeline: Pending → Confirmed → Checked-in → Checked-out / Cancelled
- Automatic room-availability sync

### 📊 Dashboard & Analytics
- Real-time stats, revenue, and occupancy tracking
- Recent-activity feed and cleaning-issue alerts

</td>
</tr>
</table>

### 📤 Data Export — Adapter Pattern in Action
Export any dataset to **CSV** or **JSON** in one click. Batch-export everything or scope it to a single module (customers, rooms, employees, etc.). Output is directly compatible with Excel, Google Sheets, and downstream web APIs.

<br>

## 🏗️ Architecture & Design Patterns

| Pattern | Where | Why it's there |
|---|---|---|
| **Singleton** | `FileUtil` | Guarantees a single, consistent point of file access across the app |
| **Adapter** | `CSVAdapter`, `JSONAdapter` | Bridges the app's internal data model to external export formats without coupling the core logic to either format |
| **Observer** | UI components | Keeps panels in sync automatically when underlying data changes |
| **Factory** | `UITheme` | Centralizes creation of consistently styled UI components |

### Adapter Pattern Structure

```
┌──────────────────────┐
│   DataExporter        │   ← Target interface
│   (interface)          │
└──────────┬────────────┘
           │
     ┌─────┴─────┐
     │           │
┌────▼────┐ ┌────▼────┐
│  CSV    │ │  JSON   │   ← Concrete adapters
│ Adapter │ │ Adapter │
└────┬────┘ └────┬────┘
     │            │
     └─────┬──────┘
           │
   ┌───────▼────────┐
   │ ExportManager   │   ← Context / client-facing API
   └────────────────┘
```

`ExportManager` never needs to know *how* an export happens — it just calls the adapter it's given, which means adding a new format (XML, PDF, …) is a matter of writing one new adapter class, not touching existing code.

<br>

## 🛠️ Tech Stack

| Layer | Technology |
|---|---|
| Language | Java 8+ |
| UI | Java Swing |
| Persistence | Java Serialization (`.dat` files) |
| Design Patterns | Singleton · Adapter · Observer · Factory |
| Build | Manual `javac` compilation |
| VCS | Git |

<br>

## 📁 Project Structure

```
hotel-management-system/
├── src/
│   └── main/
│       ├── java/
│       │   ├── AdminDashboard.java
│       │   ├── CheckOutPanel.java
│       │   ├── CustomerFormPanel.java
│       │   ├── CustomerListPanel.java
│       │   ├── DriverPanel.java
│       │   ├── EmployeePanel.java
│       │   ├── ReservationPanel.java
│       │   ├── RoomPanel.java
│       │   ├── ReportsPanel.java
│       │   ├── SettingsPanel.java
│       │   │
│       │   ├── models/
│       │   │   ├── Customer.java
│       │   │   ├── Employee.java
│       │   │   ├── HotelRoom.java
│       │   │   ├── Driver.java
│       │   │   ├── Reservation.java
│       │   │   ├── Department.java
│       │   │   └── Gender.java
│       │   │
│       │   ├── patterns/
│       │   │   ├── DataExporter.java      # Target interface
│       │   │   ├── CSVAdapter.java        # Adapter
│       │   │   ├── JSONAdapter.java       # Adapter
│       │   │   ├── ExportManager.java     # Context
│       │   │   └── FileUtil.java          # Singleton
│       │   │
│       │   └── ui/
│       │       ├── UITheme.java
│       │       └── LoginFrame.java
│       │
│       └── resources/
│           └── *.dat                       # Serialized data files
└── README.md
```

<br>

## 🚀 Getting Started

### Prerequisites
- Java JDK 8 or higher
- An IDE (IntelliJ IDEA, Eclipse, VS Code) or just a terminal

### Installation

```bash
# 1. Clone the repository
git clone https://github.com/yourusername/hotel-management-system.git
cd hotel-management-system

# 2. Compile
javac -d bin src/main/java/**/*.java

# 3. Run
java -cp bin AdminDashboard
```

### Default Login

```
Username: admin
Password: admin123
```

> ⚠️ Change the default credentials before using this in any real environment.

<br>

## 🎯 Usage Guide

**Managing employees**
1. Open **Employee Management** from the sidebar
2. Fill in name, age, department, and salary
3. Click **Add Employee**, or select a row and click **Edit**
4. The list refreshes automatically

**Room operations**
1. Go to **Room Management**
2. Add rooms with category, price, and initial status
3. Availability updates automatically on check-in/out
4. Mark rooms **Cleaned** after housekeeping passes through

**Customer check-in / check-out**
1. Open **Customer Management** and add a customer with a check-in date and room
2. Process departures from **Check-Out Panel**
3. Room availability updates automatically

**Exporting data**
1. Go to **Reports & Analytics → Export Data**
2. Pick a format — CSV or JSON
3. Choose scope — All data or a specific module
4. Select a destination folder and export

<br>

## 📊 Data Model

Data is persisted via Java Serialization, one `.dat` file per entity:

| File | Entity | Contents |
|---|---|---|
| `employees.dat` | `Employee` | Employee records, department, salary |
| `rooms.dat` | `HotelRoom` | Room category, price, availability |
| `customers.dat` | `Customer` | Customer profile and booking info |
| `reservations.dat` | `Reservation` | Bookings linking customers and rooms |
| `drivers.dat` | `Driver` | Driver profiles and vehicle details |

<br>

## 🔧 Configuration

### Theming
Adjust colors, fonts, and general look-and-feel in `UITheme.java`:

```java
public class UITheme {
    public static final Color PRIMARY_COLOR = new Color(41, 128, 185);
    public static final Color SUCCESS_COLOR = new Color(46, 204, 113);
    // ... customize as needed
}
```

### Seed data
Initial data loads automatically on first run. Adjust the defaults in `AdminDashboard.java`.

<br>

## 🎉 Quick Demo

```java
// Initialize the system
AdminDashboard dashboard = new AdminDashboard();

// Add a customer
Customer customer = new Customer(
    101, "John Doe", "+123456789", "john@email.com",
    new Date(), new Date(), 201
);
customers.add(customer);

// Export data using the Adapter pattern
ExportManager exportManager = new ExportManager();
exportManager.setExporter(new CSVAdapter());
exportManager.exportAllData("/path/to/export");
```

<br>

## 📈 Roadmap

- [ ] **Database integration** — swap file-based storage for MySQL/PostgreSQL
- [ ] **PDF export** — add a PDF adapter (e.g. via iText)
- [ ] **Email notifications** — booking confirmations on reservation
- [ ] **Payment integration** — process payments in-app
- [ ] **Internationalization** — multi-language UI
- [ ] **Visual reporting** — charts and graphs for analytics
- [ ] **REST API** — enable mobile client integration

<br>


## 🤝 Contributing

Contributions are welcome!

1. Fork the repository
2. Create a feature branch — `git checkout -b feature/amazing-feature`
3. Commit your changes — `git commit -m 'Add amazing feature'`
4. Push the branch — `git push origin feature/amazing-feature`
5. Open a Pull Request

<br>

## 📝 License

Distributed under the **MIT License**. See [`LICENSE`](LICENSE) for details.

<br>

## 🙏 Acknowledgments

- Java Swing, for a GUI toolkit that still gets the job done
- The Adapter pattern, for making the export system genuinely extensible
- Everyone who files issues, opens PRs, or just tries the app

<br>

<div align="center">

**Built with ❤️ using Java Swing**

[Report Bug](../../issues) · [Request Feature](../../issues) · [Documentation](../../wiki)

</div>
Here are the visuals of Hotel Management System in action:
![image alt](https://raw.githubusercontent.com/ttahsin124-wq/Java-project-Hotel-Management-System-/22acdc5493703e052107e2b72b72e7b28c2f9344/Screenshot%202026-01-15%20214000.png)
![image alt](https://github.com/ttahsin124-wq/Java-project-Hotel-Management-System-/blob/main/Screenshot%202026-01-15%20214022.png?raw=true)
![image alt](https://github.com/ttahsin124-wq/Java-project-Hotel-Management-System-/blob/main/Screenshot%202026-01-15%20214046.png?raw=true)
![image alt](https://github.com/ttahsin124-wq/Java-project-Hotel-Management-System-/blob/main/Screenshot%202026-01-15%20214103.png?raw=true)
![image alt](https://github.com/ttahsin124-wq/Java-project-Hotel-Management-System-/blob/main/Screenshot%202026-01-15%20214120.png?raw=true)
![image alt](https://github.com/ttahsin124-wq/Java-project-Hotel-Management-System-/blob/main/Screenshot%202026-01-15%20214138.png?raw=true)
![image alt](https://github.com/ttahsin124-wq/Java-project-Hotel-Management-System-/blob/main/Screenshot%202026-01-15%20214153.png?raw=true)
![image alt](https://github.com/ttahsin124-wq/Java-project-Hotel-Management-System-/blob/main/Screenshot%202026-01-15%20214206.png?raw=true)



