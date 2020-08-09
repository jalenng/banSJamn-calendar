# banSJamn-calendar
**SJSU CS 151 Summer 2020 Project: Calendar**\
This project is an implementation of a calendar similar to Google Calendar. The program follows the [MVC pattern](https://en.wikipedia.org/wiki/Model%E2%80%93view%E2%80%93controller).
![Screenshot](https://i.imgur.com/EVGwmud.png)

## Features
* Four views 
  * Day 
  * Week 
  * Month 
  * Agenda (a user-defined time interval) 
* Event creation (but no event exportation 😅)
* Recurring event importation from .txt file
  * Each row of semicolon-delimited items represents an event
    * Event name
    * Year
    * Starting month
    * Ending month
    * Repeating days of the week
    * Starting time
    * Ending time 
  * An example file can be found at `src/input.txt`
    ```
    CS 151 Class;2020;6;8;TH;9;11; 
    Exercise;2020;6;8;SA;15;16; 
    Winter Break;2020;12;12;SMTWHFA;0;23; 
    ...
    ```
* Two themes
  * Space ✨
  * Animals 🐢
