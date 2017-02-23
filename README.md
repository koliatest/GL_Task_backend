# GL_Task_backend
## Basic steps to setup the project:
1. clone or download project from github (git clone https://github.com/koliatest/GL_Task_backend.git)
2. open cloned directory using some IDE, in my case it is IntelliJ IDEA 14.0.2
3. in the file /src/main/java/com/kolia/config/DBConfiguration.java you should configure DB credentials for your case: 
  * PROPERTY_NAME_DATABASE_URL 
  * PROPERTY_NAME_DATABASE_PASSWORD
  * PROPERTY_NAME_DATABASE_USERNAME
4. then just run the project
* P.S. For secure API i use Basic authentication, it means, each request to API should contain header with:
  * Authorization = Basic base64(username:password)
