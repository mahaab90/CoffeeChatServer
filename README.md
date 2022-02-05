# CoffeeChatServer
To enable a distrubuted team communication, a Virtual Coffee Chat is created  
this project represents the server side of the application, the client side id available at  https://github.com/mahaab90/VirtualCoffee.Client/tree/master 

the server manage the data input and the client requests  
the format of Data is:  
an employee:      
             **Name** : name of the employee ( unique value for every employee) 
            **Offset**: offset time to GMT  
            **Availabilities**: the set of the employee availabilties in the current day     
an avalability:       
                **Id**: the availability identification 
                ** Employee**: the reference employee  
                **start**: start time  (hour value from 0 -> 23)  
                ** end**: end time (hour value from 0 -> 23) 
                
 We can communicate with the server using hhtp request:
 
 for listing the data:
  Get: http://localhost:8080/thinkiteers/ ==> all the employees with their avalailabilities
  Get: http://localhost:8080/avalailabilities/{name employee}==> the employee avalailabilities
 
 for adding new data:
 Post: http://localhost:8080/thinkiteers/new: adding the new employee passed in the request body
 Post : http://localhost:8080/avalailabilities/{name employee}/availability: adding new availability,passed in the request body, for the employee
 
 for editing availability:
 
 Put: http://localhost:8080/avalailabilities/update/{availability id} : editing the availability indicated in the request with the data passed in the request body
 
 the main service provided by the server is searching for a possible chat giving the availabilty , the offset time and the desired number of participants 
  
 request example : Get: http://localhost:8080/avalailabilities/chat?start=8&end=10&offset=1&numberOfParticipants=2  
  
  the server will look for employees with matched availabilities  
  the returned suggestion match two rules: numberOfParticipants equals to the desired number or the maximum smaller number (top priority) 
                                           the chat duration is the largesr (second priority)   
     response Example: start: 9
                     end: 11
                     [Ali,Fatma,Amin]
  
  the server response is empty if no common availability is found
  
                                          
 
                

