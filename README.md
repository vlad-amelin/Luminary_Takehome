# Luminary Take Home Exercise
This project contains basic scaffolding to provide the libraries and dependencies needed for this project to be successful.

# Problem Definition
You are given an endpoint (https://randomuser.me/api/?page=1&results=15) which contains several random generated users.
The task consist in:
  * When the app starts, check a if there is a list of cached users. If there is, return the cache list
  * If there is no users in cache, hit the endpoint, fetch the users, store them in a Database as Cache, and return the list as a result
  * Once the system gets a list of users, Display a list of the users in a recycler view.
  * The Display should consist of the user display image as a circle followed by the users first and last name.
  * If the app is force closed, upon open, the recycler view loads the data from the Database cache. 

You are expected to use the following patterns
* Dependency Injection
* Reactive data streams (i.e RxJava, Flow, Live Data)
* Database persistence
* Architecture patterns

![Image of final result](./final_result.png)

# Bonus Requirement
* If the user pulls the recycler view, the cache will be cleared and the system will hit the endpoint to get new data
  

