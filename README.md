# MIDApp 
MID is an Android app that allows you to subscribe, create tasks, edit and save them for future use.

How to use it for now:
- Get the backend from https://github.com/boh21/HBV-MID
- Read readme there to set it up on intellij
- Get the frontend here and pull it to android studio

1. Open the app. You should see the login page
2. If you have an account, log in with username and password
    a. If you enter incorrect password or username, you will get a toast about that but for now you have unlimited tries to log in.
3. If you do not have an account, you can go to the sign up page and create an account with username*, password* and email*. 
(*All required)
4. If you created an account successfully you will go to the login page and have to login
5. When you are logged in you should see all your tasks in a list
6. To create a new task press the create a new task button
7. You can create tasks with different names, due dates, priorities and categories.
8. If you dont want to create a task you can press the back button to go back to the list
9. When you have created a task successfully you should automatically return to view task page where you see a list of your tasks.
11. You can use the search function to search for a task by name and when you press enter the tasks with that name should appear
12. You can also use the filter to filter out tasks.
    - There are three types of filters in the drop down
        - Priority (High or low)
        - Category 
        - Status
    - By choosing as manu filters as you would like, you can find tasks that apply to your selected filters
         - Attention: If no priority filters (or both) are chosen, tasks with both priority criteria will be shown
         - The same goes for the category filters and status

14. You can also change view and see your tasks on a Kanban table.
13. To change your task press on a chosen task from either view.
14. You will go to another page where your task is autofilled and you can change it or delete it (you might have to scroll down a little)
15. When you have changed or deleted your task you will be taken to the view tasks page again.
16. You can manage your profile and change your password and email but not your username.
