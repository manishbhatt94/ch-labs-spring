# Output for Sample Code Run

```txt

Dropping table spring_employee in database...
Table spring_employee dropped successfully in database.


Creating table spring_employee in database...
Table spring_employee created successfully in database.


Seeding table spring_employee with 50 employees...
Batch Update Counts: [[1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1]]
Done seeding table spring_employee with 50 employees.


Inserting employee: Method Man into table spring_employee in database...
Rows affected: 1
Inserted employee: Method Man.


Inserting employee: Ol' Dirty Bastard into table spring_employee in database...
Rows affected: 1
Inserted employee: Ol' Dirty Bastard.


Updating salary of employee with ID: 1 to 11111 in table spring_employee in database...
Rows affected: 1
Updated salary of employee with ID: 1.


Updating salary of employee with ID: 2 to 22222 in table spring_employee in database...
Rows affected: 1
Updated salary of employee with ID: 2.


Updating salary of employee with ID: 3 to 33333 in table spring_employee in database...
Rows affected: 1
Updated salary of employee with ID: 3.


Deleting employees with ID between 11 and 15...
Rows affected: 5
Deleted employees.


Getting list of employees having salary below the average salary in the spring_employee table...
Found 21 employee(s) having salary below average.
Records fetched below:

    ->  employee_id=1;  employee_name=Arjun Sharma;  employee_address=Indiranagar, Bangalore, Karnataka;  employee_salary=11111;  
    ->  employee_id=2;  employee_name=Priya Nair;  employee_address=Panampilly Nagar, Kochi, Kerala;  employee_salary=22222;  
    ->  employee_id=3;  employee_name=Ravi Kumar;  employee_address=Madhapur, Hyderabad, Telangana;  employee_salary=33333;  
    ->  employee_id=5;  employee_name=Vikram Singh;  employee_address=Malviya Nagar, Jaipur, Rajasthan;  employee_salary=45000;  
    ->  employee_id=6;  employee_name=Ananya Gupta;  employee_address=Hazratganj, Lucknow, Uttar Pradesh;  employee_salary=47000;  
    ->  employee_id=10;  employee_name=Meena Joshi;  employee_address=Vijay Nagar, Indore, Madhya Pradesh;  employee_salary=46000;  
    ->  employee_id=16;  employee_name=Radhika Menon;  employee_address=Kadri, Mangalore, Karnataka;  employee_salary=48000;  
    ->  employee_id=17;  employee_name=Sanjay Yadav;  employee_address=Kankarbagh, Patna, Bihar;  employee_salary=44000;  
    ->  employee_id=19;  employee_name=Deepak Mishra;  employee_address=Assi Ghat, Varanasi, Uttar Pradesh;  employee_salary=47000;  
    ->  employee_id=23;  employee_name=Nikhil Jain;  employee_address=C-Scheme, Jaipur, Rajasthan;  employee_salary=46000;  
    ->  employee_id=26;  employee_name=Pallavi Kulkarni;  employee_address=Shivajinagar, Pune, Maharashtra;  employee_salary=49000;  
    ->  employee_id=27;  employee_name=Harish Chandra;  employee_address=Rajendra Nagar, Patna, Bihar;  employee_salary=45000;  
    ->  employee_id=28;  employee_name=Geeta Shukla;  employee_address=Aliganj, Lucknow, Uttar Pradesh;  employee_salary=48000;  
    ->  employee_id=30;  employee_name=Meera Joshi;  employee_address=MG Road, Indore, Madhya Pradesh;  employee_salary=47000;  
    ->  employee_id=33;  employee_name=Rajeev Ranjan;  employee_address=Fraser Road, Patna, Bihar;  employee_salary=43000;  
    ->  employee_id=39;  employee_name=Rekha Sharma;  employee_address=Lalbagh, Lucknow, Uttar Pradesh;  employee_salary=47000;  
    ->  employee_id=42;  employee_name=Neelam Sinha;  employee_address=Kankarbagh, Patna, Bihar;  employee_salary=45000;  
    ->  employee_id=45;  employee_name=Gaurav Mishra;  employee_address=Civil Lines, Allahabad, Uttar Pradesh;  employee_salary=47000;  
    ->  employee_id=47;  employee_name=Naveen Kumar;  employee_address=Sector 15, Chandigarh;  employee_salary=48000;  
    ->  employee_id=51;  employee_name=Method Man;  employee_address=Park Hill Project, Staten Island, New York;  employee_salary=23000;  
    ->  employee_id=52;  employee_name=Ol' Dirty Bastard;  employee_address=Park Hill Project, Staten Island, New York;  employee_salary=31000;  

Done reading all below average salary employees.


Getting list of employees residing in Karnataka state...
Found 6 employee(s) residing in Karnataka state.
Records fetched below:

    ->  Employee [id=1, name=Arjun Sharma, address=Indiranagar, Bangalore, Karnataka, salary=11111]
    ->  Employee [id=16, name=Radhika Menon, address=Kadri, Mangalore, Karnataka, salary=48000]
    ->  Employee [id=20, name=Kavya Shetty, address=Manipal, Udupi, Karnataka, salary=52000]
    ->  Employee [id=25, name=Ramesh Gowda, address=Basavanagudi, Bangalore, Karnataka, salary=57000]
    ->  Employee [id=34, name=Kiran Kumar, address=Whitefield, Bangalore, Karnataka, salary=65000]
    ->  Employee [id=43, name=Ashwini Rao, address=Jayanagar, Bangalore, Karnataka, salary=60000]

Done reading all Karnataka state resident employees.


Getting of employees having 'Kulkarni' in their name...
Found 2 employee(s) having 'Kulkarni' in their name.
Records fetched below:

    ->  Employee [id=26, name=Pallavi Kulkarni, address=Shivajinagar, Pune, Maharashtra, salary=49000]
    ->  Employee [id=46, name=Smita Kulkarni, address=Camp, Pune, Maharashtra, salary=52000]

Done reading all employees matching name substring.


Fetching employee having employee_id = 3017...

Employee with employee_id = 3017 -- NOT FOUND!


Fetching employee having employee_id = 19...

Employee with employee_id = 19 -- FOUND!
Found employee: Employee [id=19, name=Deepak Mishra, address=Assi Ghat, Varanasi, Uttar Pradesh, salary=47000].


Inserting employee: Inspectah Deck into table spring_employee in database...
Rows affected: 1
Inserted record's auto generated primary key = 53
Inserted employee: Inspectah Deck.


Getting count of employee records...
Row count: 48.


Getting state-wise counts of employees...
Retrieved 14 rows(s) -- i.e. this many states.
Records fetched below:

    ->  employee_state = Uttar Pradesh       state_employee_count = 7                   
    ->  employee_state = Maharashtra         state_employee_count = 7                   
    ->  employee_state = Karnataka           state_employee_count = 6                   
    ->  employee_state = Kerala              state_employee_count = 5                   
    ->  employee_state = Bihar               state_employee_count = 4                   
    ->  employee_state = Tamil Nadu          state_employee_count = 3                   
    ->  employee_state = New York            state_employee_count = 3                   
    ->  employee_state = Telangana           state_employee_count = 2                   
    ->  employee_state = Rajasthan           state_employee_count = 2                   
    ->  employee_state = Madhya Pradesh      state_employee_count = 2                   
    ->  employee_state = Delhi               state_employee_count = 2                   
    ->  employee_state = Chandigarh          state_employee_count = 2                   
    ->  employee_state = Haryana             state_employee_count = 2                   
    ->  employee_state = Punjab              state_employee_count = 1                   

Done reading state-wise counts of employees.


Fetching employees with salary >= 55000 residing in state containing 'Karnataka'...
Found 3 employee(s) matching criteria.
Records fetched below:

    ->  Employee [id=34, name=Kiran Kumar, address=Whitefield, Bangalore, Karnataka, salary=65000]
    ->  Employee [id=43, name=Ashwini Rao, address=Jayanagar, Bangalore, Karnataka, salary=60000]
    ->  Employee [id=25, name=Ramesh Gowda, address=Basavanagudi, Bangalore, Karnataka, salary=57000]

Done reading employees by salary and state.


Fetching employees with salary >= 50000 residing in state containing 'Uttar Pradesh'...
Found 2 employee(s) matching criteria.
Records fetched below:

    ->  Employee [id=36, name=Aakash Singh, address=Sector 62, Noida, Uttar Pradesh, salary=61000]
    ->  Employee [id=48, name=Poonam Yadav, address=Sector 22, Noida, Uttar Pradesh, salary=50000]

Done reading employees by salary and state.


```

