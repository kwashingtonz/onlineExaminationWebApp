# onlineExaminationWebApp

Pull the repository to a folder

import the project

Create database "onlineExam_db" in your mysqlserver and restore it by using the sql file available in folder database/onlineExam_db.sql.

Then, change the src/main/resources/application.properties attributes to match your enviroment variables.
Ex: change spring.datasource.username and spring.datasource.password values to your mysql user username and password. 

Execute as a Java Application


 2 Roles available:
 Teacher - (teacher1) email = teacher@gmail.com	password = teacher123, (teacher2) email = teacher2@gmail.com	password = teacher123
 Student - (student1) email = student@gmail.com	password = student123, (student2) email = student2@gmail.com	password = student123
