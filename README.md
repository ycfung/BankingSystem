# Banking System

## Background

The system is expected to handle account creation, deposit,withdrawal and transfer.

## Compile

This project is developed using pure JDK1.8, and JUnit 5.8.1 is used for testing. 

No other dependency is needed.

## Usage

You can run the Main class to test the program.

## Description

The MVC architecture is applied to this project, also obeying the SOLID principle.

Due to the time limit, I did not write JUnit tests to cover all classes, but the essential part of this project was tested.

Please note that there is an extra entry in the main menu. It's for testing. Testers can use this entry to change the current user to check if the data is consistent.

The login process and password verification are not mentioned in the bank account structure. I decided to skip these features and focus on the operation functions.

## Directory 

```
.
├─.idea
├─out
│  ├─production
│  │  └─BankingSystem
│  │      └─com
│  │          └─rcf
│  │              └─banking
│  │                  ├─controller
│  │                  ├─entity
│  │                  ├─exception
│  │                  ├─repository
│  │                  ├─service
│  │                  └─util
│  └─test
│      └─BankingSystem
│          └─com
│              └─rcf
│                  └─banking
└─src
    ├─com
    │  └─rcf
    │      └─banking
    │          ├─controller
    │          ├─entity
    │          ├─exception
    │          ├─repository
    │          ├─service
    │          └─util
    └─test
        └─com
            └─rcf
                └─banking
```

