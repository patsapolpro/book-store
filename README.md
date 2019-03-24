Bookstore RESTful API
===

    #### Develop a RESTful API for a	bookstore that allows a	user to	login, perform user	related	tasks, view	a list of books and	place book orders.
    #### You should develop the API using Java Spring Boot and commit your work, setup instructions and any cURL scripts to a remote git repository	(github, gitlab, bitbucket,	etc).
    #### We	expect	that you demonstrate the following in your solution:
        * applied SOLID	principles
        * error	and	exception handling
        * security
        * database design
        * atomic commits to	repository
    #### You will have our extra attention if you also provide the following:
        * unit	tests
        * performance optimization
        * sequence	diagrams
        * API	documentation	(Swagger/RAML)
    #### Should there be any missing requirements in	this document, you	may	exercise your own discretion.

    #### Below are the APIs that	you	would need to implement:

    ## Book Store API

    #### POST /login
    This is	the	user login authentication API. The request and response	should be over a secured communication
    |                               |                                                                    |
    | :---------------------------- | :----------------------------------------------------------------  |
    | Request method:               |  POST                                                              |
    | Request URL:                  |  /login                                                            |
    | Request Header:               |  Content-Type: application/json                                    |
    | Request Body:                 |  {"username":"john.doe",	"password":	"thisismysecret"}            |
    | Response HTTP Status Code:    |  200 OK                                                            |

    #### GET /users
    (Login required) Gets information about the	logged	in	user. A	successfully authenticated request returns
    information	related	to the user	and	the	books ordered
    |                               |                                                                    |
    | :---------------------------- | :----------------------------------------------------------------  |
    | Request method:               |  GET                                                               |
    | Request URL:                  |  /users                                                            |
    | Request Header:               |  Content-Type: application/json                                    |
    | Response HTTP Status Code:    |  200 OK                                                            |
    | Response Body:                |  { "name": "john", "surname":	"doe", "date_of_birth":	"15/01/1985",|
    |                               |    "books": [1, 4] }                                               |

    #### DELETE /users
    (Login required) Delete	logged in user’s record	and	order history.
    |                               |                                                                    |
    | :---------------------------- | :----------------------------------------------------------------  |
    | Request method:               |  DELETE                                                            |
    | Request URL:                  |  /users                                                            |
    | Request Header:               |  Content-Type: application/json                                    |
    | Response HTTP Status Code:    |  200 OK                                                            |

    #### POST /users
    (Login  not required)   Create a user account and store	user’s information in Users table (DB)
    |                               |                                                                    |
    | :---------------------------- | :----------------------------------------------------------------  |
    | Request method:               |  POST                                                              |
    | Request URL:                  |  /users                                                            |
    | Request Header:               |  Content-Type: application/json                                    |
    | Request Body:                 |  {"username": "john.doe", "password": "thisismysecret",            |
    |                                   "date_of_birth":	"15/01/1985"}                                |
    | Response HTTP Status Code:    |  200 OK                                                            |

    #### POST /users/orders
    (Login required) Order books and store order information in	Orders table (DB). This	returns	the	price for a
    successful order.
    |                               |                                                                    |
    | :---------------------------- | :----------------------------------------------------------------  |
    | Request method:               |  POST                                                              |
    | Request URL:                  |  /users/orders                                                     |
    | Request Header:               |  Content-Type: application/json                                    |
    | Request Body:                 |  {"orders": [1, 4]}                                                |
    | Response HTTP Status Code:    |  200 OK                                                            |
    | Response Body:                |  {"price": 519.00}                                                 |

    #### GET /books
    (Login not required)    Gets a list	of books from an external book publisher’s web services	and	returns	the
    list sorted	alphabetically with the	recommended books always appears first.	The	should be no duplicated books
    in the list.
    |                               |                                                                    |
    | :---------------------------- | :----------------------------------------------------------------  |
    | Request method:               |  GET                                                               |
    | Request URL:                  |  /books                                                            |
    | Response HTTP Status Code:    |  200 OK                                                            |
    | Response Body:                |  {                                                                 |
    |                               |         "books":	[                                                |
    |                               |              {                                                     |
    |                               |                "id":  1,                                           |
    |                               |                 "name":"An  American Princess",	                 |
    |                               |                 "author":"Annejet van der Zijl",	                 |
    |                               |                 "price":149.00,	                                 |
    |                               |                 "is_recommended": true                             |
    |                               |              },                                                    |
    |                               |              {                                                     |
    |                               |                 "id":  2,                                          |
    |                               |                 "name":"Not An American Princess",	             |
    |                               |                 "author":"Annejet van der Zijl",	                 |
    |                               |                 "price":22.00,	                                 |
    |                               |                 "is_recommended": false                            |
    |                               |              }                                                     |
    |                               |         ]                                                          |
    |                               |    }                                                               |


