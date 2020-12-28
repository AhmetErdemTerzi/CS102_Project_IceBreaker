Group Number: G2A

Title: Icebreaker App

Description: Android application that will increase the interaction between individuals that cannot extend their social networks in the period of the pandemic. Users will join lobbies and attend activities. Two can interact with each other by giving or receiving tasks/questions. There were predetermined tasks or questions that can be pulled from the database to be used in the application. There are different categories for tasks. When the tasks are done, interactions are completed and users get points according to how successful they are. The points will be given by the one who asks. 


Current Status: Completely finished with possible bugs.

Work Done: Users, Activities, Tasks (Global task1, global task2, orienteering, direct task, quiz), Lobby, notifications were all completed as stated in the proposal.

Remaining Done: Project is completely finished with possible bugs.

Contributions: Contributions are below. GitHub can also be examined for details.

Emrecan Kutay:

Question
Quiz Activity
Direct Task
Task Receiver Activity
Task Giver Activity
Outdoor ScoreBoard
Outdoor Scoreboard Activity
ScoreBoard 
Corrections in Lobby
Bug fixing, correction of view in some classes.
Construction of database (Questions, tasks)

Aral Salehyan:

Global Task 2
Outdoor ScoreBoard Main Activity
Corrections in Lobby
ScoreBoard Main Activity

Ahmet Erdem Terzi

Global Task 1
Outdoor Event Main Activity
Corrections in Lobby
General view design
Helped others in their parts and bug fixing

Özmen Erkin Kökten
User
Admin User
Event
Lobby
Lobby Activity
User Tab
ValidateInput
Splash Activity
Signup Activity
Review Suggestions Activity
Play Tab Activity
Notification Activity
Main Activity

Barkın Saday


Used Software: 
Android Studio - 4.1
Firebase - Online Console

Used Libraries:
For each class and activity, the imported classes are listed below:

AdminUser:
com.google.android.gms.tasks.OnCompleteListener;
com.google.android.gms.tasks.Task;
com.google.firebase.database.DataSnapshot;
com.google.firebase.database.DatabaseError;
com.google.firebase.database.FirebaseDatabase; com.google.firebase.database.ValueEventListener;
com.google.firebase.firestore.FirebaseFirestore;
com.google.firebase.firestore.QuerySnapshot;
java.util.HashMap;
java.util.Map;
androidx.annotation.NonNull;

DirectTask:
androidx.annotation.NonNull;
com.google.android.gms.tasks.OnCompleteListener;
com.google.android.gms.tasks.Task;
com.google.firebase.database.DataSnapshot;
com.google.firebase.database.DatabaseError;
com.google.firebase.database.DatabaseReference;
com.google.firebase.database.FirebaseDatabase;
com.google.firebase.database.ValueEventListener;
com.google.firebase.firestore.DocumentSnapshot;
com.google.firebase.firestore.FirebaseFirestore;
com.google.firebase.firestore.QuerySnapshot;
java.util.*;


GlobalTask1:
androidx.annotation.NonNull;
androidx.appcompat.app.AppCompatActivity;
androidx.core.app.ActivityCompat;
androidx.core.content.ContextCompat;
android.Manifest;
android.annotation.SuppressLint;
android.app.Dialog;
android.content.Intent;
android.content.IntentSender;
android.content.pm.PackageManager;
android.graphics.Color;
android.graphics.drawable.ColorDrawable;
android.location.Location;
android.os.Bundle;
android.os.CountDownTimer;
android.os.Looper;
android.util.Log;
android.view.View;
android.widget.Button;
android.widget.ImageView;
android.widget.TextView;
com.google.android.gms.common.api.ResolvableApiException;
com.google.android.gms.location.FusedLocationProviderClient;
com.google.android.gms.location.LocationCallback;
com.google.android.gms.location.LocationRequest;
com.google.android.gms.location.LocationResult;
com.google.android.gms.location.LocationServices;
com.google.android.gms.location.LocationSettingsRequest;
com.google.android.gms.location.LocationSettingsResponse;
com.google.android.gms.location.SettingsClient;
com.google.android.gms.maps.CameraUpdate;
com.google.android.gms.maps.CameraUpdateFactory;
com.google.android.gms.maps.GoogleMap;
com.google.android.gms.maps.OnMapReadyCallback;
com.google.android.gms.maps.SupportMapFragment;
com.google.android.gms.maps.model.BitmapDescriptorFactory;
com.google.android.gms.maps.model.LatLng;
com.google.android.gms.maps.model.Marker;
com.google.android.gms.maps.model.MarkerOptions;
com.google.android.gms.tasks.OnFailureListener;
com.google.android.gms.tasks.OnSuccessListener;
com.google.firebase.database.DataSnapshot;
com.google.firebase.database.DatabaseError;
com.google.firebase.database.FirebaseDatabase;
com.google.firebase.database.ValueEventListener;
java.util.Timer;

GlobalTask2:
androidx.annotation.NonNull;
androidx.appcompat.app.AppCompatActivity;
androidx.core.content.ContextCompat;
android.Manifest;
android.content.pm.PackageManager;
android.app.Dialog;
android.content.Intent;
com.google.firebase.database.FirebaseDatabase;
com.google.firebase.database.ValueEventListener;
android.hardware.Sensor;
android.hardware.SensorEvent;
android.hardware.SensorEventListener;
android.hardware.SensorManager;
android.os.Bundle;
android.widget.TextView;
android.widget.Button;
android.widget.ImageView;
android.view.View;
android.graphics.Color;
android.graphics.drawable.ColorDrawable;
android.os.CountDownTimer;
com.google.firebase.database.DataSnapshot;
com.google.firebase.database.DatabaseError;
com.mikhaellopez.circularprogressbar.CircularProgressBar;
java.util.*;

Lobby:
com.google.firebase.database.DataSnapshot;
com.google.firebase.database.DatabaseError;
com.google.firebase.database.DatabaseReference;
com.google.firebase.database.FirebaseDatabase;
com.google.firebase.database.ValueEventListener;
java.util.ArrayList;
androidx.annotation.NonNull;

LobbyActivity:
androidx.annotation.NonNull;
androidx.appcompat.app.AlertDialog;
androidx.appcompat.app.AppCompatActivity;
android.content.DialogInterface;
android.content.Intent;
android.graphics.Color;
android.os.Bundle;
android.view.View;
android.widget.Button;
android.widget.TextView;
com.google.android.gms.tasks.OnSuccessListener;
com.google.firebase.database.DataSnapshot;
com.google.firebase.database.DatabaseError;
com.google.firebase.database.FirebaseDatabase;
com.google.firebase.database.ValueEventListener;
com.google.firebase.firestore.DocumentSnapshot;
com.google.firebase.firestore.FirebaseFirestore;
java.util.ArrayList;
java.util.Timer;
java.util.TimerTask;
android.widget.Toast;

MainActivity:
androidx.annotation.NonNull;
androidx.appcompat.app.AppCompatActivity;
android.content.Intent;
android.os.Bundle;
android.view.View;
android.widget.Button;
android.widget.EditText;
android.widget.TextView;
android.widget.Toast;
com.google.android.gms.tasks.OnCompleteListener;
com.google.android.gms.tasks.Task;
com.google.firebase.auth.AuthResult;
com.google.firebase.auth.FirebaseAuth;

notifActivity:
androidx.annotation.NonNull;
androidx.appcompat.app.AppCompatActivity;
android.content.Intent;
android.graphics.Color;
android.os.Bundle;
android.view.View;
android.widget.Button;
android.widget.TextView;
com.google.firebase.database.DataSnapshot;
com.google.firebase.database.DatabaseError;
com.google.firebase.database.DatabaseReference;
com.google.firebase.database.FirebaseDatabase;
com.google.firebase.database.ValueEventListener;
java.util.ArrayList;

OutdoorEventMainActivity:
android.content.DialogInterface;
android.content.Intent;
android.graphics.Color;
android.os.Bundle;
android.view.View;
android.widget.Button;
android.widget.EditText;
android.widget.ImageButton;
android.widget.Toast;
androidx.annotation.NonNull;
androidx.appcompat.app.AlertDialog;
androidx.appcompat.app.AppCompatActivity;
com.google.android.gms.tasks.OnCompleteListener;
com.google.android.gms.tasks.Task;
com.google.firebase.database.DataSnapshot;
com.google.firebase.database.DatabaseError;
com.google.firebase.database.FirebaseDatabase;
com.google.firebase.database.ValueEventListener;
com.google.firebase.firestore.FirebaseFirestore;
com.google.firebase.firestore.QueryDocumentSnapshot;
com.google.firebase.firestore.QuerySnapshot;
java.util.ArrayList;

OutdoorScoreBoard:
android.os.Handler;
androidx.annotation.NonNull;
com.google.android.gms.tasks.OnCompleteListener;
com.google.android.gms.tasks.Task;
com.google.firebase.database.DataSnapshot;
com.google.firebase.database.DatabaseError;
com.google.firebase.database.DatabaseReference;
com.google.firebase.database.FirebaseDatabase;
com.google.firebase.database.ValueEventListener;
com.google.firebase.firestore.DocumentSnapshot;
com.google.firebase.firestore.FirebaseFirestore;
com.google.firebase.firestore.QuerySnapshot;
java.util.ArrayList;


OutdoorScoreBoardActivity:
android.content.DialogInterface;
android.content.Intent;
android.os.Bundle;
android.os.Handler;
android.view.View;
android.widget.AdapterView;
android.widget.ArrayAdapter;
android.widget.Button;
android.widget.GridView;
android.widget.Toast;
androidx.annotation.NonNull;
androidx.appcompat.app.AlertDialog;
androidx.appcompat.app.AppCompatActivity;
com.google.android.gms.tasks.OnSuccessListener;
com.google.firebase.database.DataSnapshot;
com.google.firebase.database.DatabaseError;
com.google.firebase.database.DatabaseReference;
com.google.firebase.database.FirebaseDatabase;
com.google.firebase.database.ValueEventListener;
com.google.firebase.firestore.DocumentSnapshot;
com.google.firebase.firestore.FirebaseFirestore;
java.util.ArrayList;
java.util.HashMap;
java.util.Map;

playTabActivity:
androidx.annotation.NonNull;
androidx.appcompat.app.AppCompatActivity;
android.content.Intent;
android.os.Bundle;
android.util.Log;
android.view.View;
android.widget.ArrayAdapter;
android.widget.Button;
android.widget.EditText;
android.widget.Spinner;
android.widget.Toast;
com.google.android.gms.tasks.OnCompleteListener;
com.google.android.gms.tasks.OnFailureListener;
com.google.android.gms.tasks.OnSuccessListener;
com.google.android.gms.tasks.Task;
com.google.firebase.database.DataSnapshot;
com.google.firebase.database.DatabaseError;
com.google.firebase.database.DatabaseReference;
com.google.firebase.database.FirebaseDatabase;
com.google.firebase.database.ValueEventListener;
com.google.firebase.firestore.DocumentReference;
com.google.firebase.firestore.FirebaseFirestore;
com.google.firebase.firestore.QueryDocumentSnapshot;
com.google.firebase.firestore.QuerySnapshot;
java.util.ArrayList;
java.util.HashMap;
java.util.Map;


QuizActivity:
androidx.annotation.NonNull;
androidx.appcompat.app.AlertDialog;
androidx.appcompat.app.AppCompatActivity;
android.content.DialogInterface;
android.content.Intent;
android.graphics.Color;
android.os.Bundle;
android.os.CountDownTimer;
android.os.Handler;
android.view.View;
android.widget.Button;
android.widget.TextView;
com.google.android.gms.tasks.OnCompleteListener;
com.google.android.gms.tasks.Task;
com.google.firebase.database.DatabaseReference;
com.google.firebase.database.FirebaseDatabase;
com.google.firebase.firestore.FirebaseFirestore;
com.google.firebase.firestore.QueryDocumentSnapshot;
com.google.firebase.firestore.QuerySnapshot;
java.util.*;
java.util.concurrent.ThreadLocalRandom;



reviewSuggestionsActivity:
androidx.annotation.NonNull;
androidx.appcompat.app.AppCompatActivity;
android.content.Intent;
android.graphics.Color;
android.os.Bundle;
android.view.View;
android.widget.Button;
android.widget.LinearLayout;
android.widget.TextView;
com.google.firebase.database.DataSnapshot;
com.google.firebase.database.DatabaseError;
com.google.firebase.database.FirebaseDatabase;
com.google.firebase.database.ValueEventListener;
java.util.ArrayList;

ScoreBoard:
android.os.Handler;
androidx.annotation.NonNull;
com.google.android.gms.tasks.OnCompleteListener;
com.google.android.gms.tasks.Task;
com.google.firebase.database.DatabaseReference;
com.google.firebase.database.FirebaseDatabase;
com.google.firebase.firestore.FirebaseFirestore;
com.google.firebase.firestore.QueryDocumentSnapshot;
com.google.firebase.firestore.QuerySnapshot;
java.util.ArrayList;


ScoreBoardActivity:
android.content.Intent;
android.os.Bundle;
android.view.View;
android.widget.Button;
android.widget.GridView;
android.widget.ArrayAdapter;
androidx.annotation.NonNull;
androidx.appcompat.app.AppCompatActivity;
com.google.android.gms.tasks.OnSuccessListener;
com.google.firebase.database.DataSnapshot;
com.google.firebase.database.DatabaseError;
com.google.firebase.database.FirebaseDatabase;
com.google.firebase.database.ValueEventListener;
com.google.firebase.firestore.DocumentSnapshot;
com.google.firebase.firestore.FirebaseFirestore;
java.util.ArrayList;
java.util.HashMap;
java.util.Map;


SignUpActivity:
androidx.annotation.NonNull;
androidx.appcompat.app.AppCompatActivity;
android.content.Intent;
android.os.Bundle;
android.view.View;
android.widget.Button;
android.widget.EditText;
android.widget.Toast;
com.google.android.gms.tasks.OnCompleteListener;
com.google.android.gms.tasks.Task;
com.google.firebase.auth.AuthResult;
com.google.firebase.auth.FirebaseAuth;
com.google.firebase.auth.FirebaseUser;
com.google.firebase.database.DatabaseReference;
com.google.firebase.database.FirebaseDatabase;
com.google.firebase.firestore.FirebaseFirestore;
java.util.HashMap;
java.util.Map;


SplashActivity:
androidx.appcompat.app.AppCompatActivity;
android.content.Intent;
android.os.Bundle;
android.os.Handler;
android.view.View;
android.widget.ImageView;
com.google.firebase.auth.FirebaseAuth;
com.google.firebase.auth.FirebaseUser;




Task:
java.util.Timer;

TaskGiverActivity:
android.app.Dialog;
android.content.Intent;
android.graphics.Color;
android.graphics.drawable.ColorDrawable;
android.os.Bundle;
android.os.CountDownTimer;
android.view.View;
android.widget.Button;
android.widget.ImageView;
android.widget.TextView;
androidx.annotation.NonNull;
androidx.annotation.Nullable;
androidx.appcompat.app.AppCompatActivity;
com.google.firebase.database.DataSnapshot;
com.google.firebase.database.DatabaseError;
com.google.firebase.database.DatabaseReference;
com.google.firebase.database.FirebaseDatabase;
com.google.firebase.database.ValueEventListener;


TaskReceiverActivity:
android.app.Dialog;
android.content.Intent;
android.graphics.Color;
android.graphics.drawable.ColorDrawable;
android.os.Bundle;
android.os.CountDownTimer;
android.view.View;
android.widget.Button;
android.widget.ImageView;
android.widget.TextView;
androidx.annotation.NonNull;
androidx.annotation.Nullable;
androidx.appcompat.app.AppCompatActivity;
com.google.firebase.database.DataSnapshot;
com.google.firebase.database.DatabaseError;
com.google.firebase.database.DatabaseReference;
com.google.firebase.database.FirebaseDatabase;
com.google.firebase.database.ValueEventListener;

User:
com.google.firebase.auth.FirebaseAuth;
com.google.firebase.auth.FirebaseUser;
com.google.firebase.database.DataSnapshot;
com.google.firebase.database.DatabaseError;
com.google.firebase.database.DatabaseReference;
com.google.firebase.database.FirebaseDatabase;
com.google.firebase.database.ValueEventListener;
com.google.firebase.firestore.DocumentReference;
com.google.firebase.firestore.FirebaseFirestore;
androidx.annotation.NonNull;
java.util.HashMap;
java.util.Map;

UserTab:
androidx.annotation.NonNull;
androidx.annotation.Nullable;
androidx.appcompat.app.AlertDialog;
androidx.appcompat.app.AppCompatActivity;
android.content.DialogInterface;
android.content.Intent;
android.os.Bundle;
android.view.View;
android.widget.Button;
android.widget.EditText;
android.widget.LinearLayout;
android.widget.TextView;
android.widget.Toast;
com.google.firebase.auth.FirebaseAuth;
com.google.firebase.auth.FirebaseUser;
com.google.firebase.database.ChildEventListener;
com.google.firebase.database.DataSnapshot;
com.google.firebase.database.DatabaseError;
com.google.firebase.database.DatabaseReference;
com.google.firebase.database.FirebaseDatabase;
com.google.firebase.database.ValueEventListener;

ValidateInput:
android.content.Context;
android.util.Patterns;
android.widget.Toast;


Instructions for usage:  

SETUP: APK file should be installed in the android phone. Internet connection is required during the application usage. Unreliable internet connections may cause errors. 

Initially, a user needs an account to use the application. If he/she has an account, he/she can log in. Else, there is a signup button for the users to create a new account. After the login the user will see the user tab.

In user tab, username, win count, average point and total played games can be seen under “User Details”. There is a part at the bottom to suggest new tasks in Direct Task. User can log out using the Logout button.

In the play tab, there is part to join a lobby using lobby code at the top. In order to create a game, user can use the Game Type Selection Bar, Number Of players bay and create button. After creation, creater is assigned as a lobby leader and have a right to kick users in lobby. When lobby leader starts the activity, activity is started automatically for every player in the lobby. 

In the Notification tab, notifications sent by the admin users can be seen. Also, a notification is send automatically when an admin creates a lobby in order to let more people join. 


In Quiz Activity, random 5 questions were selected from the database and given to users. Users in the same lobby receives same random questions. Different lobbies have different randoms. The remaining time, question count and score can be seen at the top. Users should answer correctly in the specified time. After 5 questions. users move to scoreboard automatically and the win count is incremented for the winner user.

In Outdoor Activity, users encounter with Global Task 1 , Global Task 2 , Orienteering Code and ScoreBoard tab. In global task1, users are asked to share their locations. After sharing, a random location is set within a specific range and users should go to the specific point in a given time to earn points (should reach the area with error margin). In global task2, a random number of steps are generated. Users should walk and reach the step number in a given time to earn points. In addition, there are orienteering codes set, which users earn points if they enter. In scoreboard, users are able to give task to each other by clicking on the names. A request is send to task receiver. Requests are chosen randomly from the database. Failure and success was determined by the task giver using the buttons. Task receiver can set failure if he/she thinks its too hard. There is also a time limit.

In outdoor event, event ends when a user reaches 80 points. All users are moved to end scoreboard automatically and it was shown, winner incremented.

There are exit buttons in the XMLs which allow users to leave whenever they want.


FOR ADMIN USER ONLY,

In admin user, there are additional two buttons at the bottom to review the send task suggestions. Admins decide whether accept or reject the new task requests. There is also an additional part in the notification tab to send notifications to users or delete current notifications. If they create a lobby, notification will be send to users automatically.

