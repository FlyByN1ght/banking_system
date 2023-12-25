# **Banking System**

A program for processing bank transactions using files.

# **Description**

This application is designed to automate the processing of banking transactions. The program reads data from files, processes transactions and keeps a log of transactions.

# **Usage**
1. Place the files to be processed in the 'Input' directory.
2. Launch the application.
3. The processing results will be written to the 'report.txt' file, and the processed files will be moved to the 'Archive' directory.
   
# **Project structure**
src

model

AccountReader.java - class for reading and updating account data.

EmptyDirectoryException.java is a custom exception for an empty directory.

EmptyFileException.java is a custom exception for an empty file.

FileParse.java is a class for processing files and performing transactions.

FileRead.java - class for reading data from files.

ReportFileHandler.java - class for logging operations.

# **Dependencies**
Java 8 or higher

# **Launch**
1. Clone the repository: git clone (https://github.com/FlyByN1ght/banking_system)
2. Go to the 'banking_system/src' directory
3. Run the program: javac model/FileParse.java && java model.FileParse

# **Сlass diagram**
![image](https://github.com/FlyByN1ght/banking_system/assets/90777434/1ed56589-0469-4fd7-a632-052a9db700fe)


