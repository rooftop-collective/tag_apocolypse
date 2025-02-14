# JavaTag: Code 401 Final Project

## APK
To download and play Zombie Tag click the zombie below  

[![Build Status](https://github.com/JavaAwesome/JavaTag/blob/Dev/app/src/main/res/mipmap-hdpi/ic_launcher_round.png)](https://github.com/JavaAwesome/JavaTag/blob/Dev/zombietag.apk)

## Team Members
* Ahren Swett
* James Dansie
* Jon Veach
* Quang Nguyen
* Sarah Fisher
* Sharina Stubbs

## Version
1.0.0

## Problem Domain
To make an android app where players can see one another on a map and tag one another.

## Deployment

## Instructions to run the app on your phone

### Contribute
No contribution guidelines at this point.

## Build Status
### User Stories

### Wireframes
![wire frame](./assets/Page_1.png)
![wire frame](./assets/Page_2.png)
![wire frame](./assets/Page_3.png)

### Project Scope
#### MVP
* Minimum of two players
* Possible tag definitions, may change as we go:
    * By distance
    * By QR Code
    * By taking a picture of them
    * By facial recognition
* Tag notifications

#### Stretch goals
* Showing multiple players on a map
* Geo fence (bounds)
* Control who can see the game by using authentication
* Bread crumbs

### Daily Team Workflow
#### Day 1
* Implemented the Google Maps object
    * TAG possibility of Distance implemented
    * TAG possibility of tapping tag button partially implemented
* Figured out how to pass data to Google Maps to render pins
* Setup DynamoDB
    * Player model
    * Session model
* Created activities
    * Map
    * Main
* Setup Cognito
* Created recyclerview to pull current sessions
* Created dummy team and session, loaded it to the database
* Started logic to utilize tagging by pictures
* Started logic to update which user is tagged  

#### Day 2
* Added permissions 
* Fixed recycler view :D
* Creating a session and passing in the users lat/lon to create the boundary
* Querying the database for sessions
* Modified initializeMarkerAndPlayers to allow its self to override
    * Sets the markers and colors based on players
    * Now called from callback from querying the database
* Created a query for a selected session
    * Creates an instance based on that ID
    * Creates the starting point based on the users lat/lon
    * Converting players from database back into players
        * Created overloaded constructor in Player to allow this
* Completed logic to render players to map
    * Still need to add refresh logic to the render
    * Validated if a user is already in a session or not
* Started fun logic when player is tagged to show some sort of notification
    * Figured out Vibrator!
* Allowed players to join existing sessions  

#### Day 3
* Camera logic is mostly config complete, need to save and pull from database
    * Added a new permission for CAMERA
* Figured out more logic of updating users in the database
    * A users lat/lon will now update when moving
* Created logic to prevent duplicate users each time we login/start app
* Completed logic to render players on a map live!
    * When a player joins a game, it creates their initial markers
        * When a player moves, the markers moves
	     * When it marker collides with not it, not it becomes tagged
	     * When a player creates a session, it no longer crashes
* Started styling the app!
    * Figured out how to create a custom icon, theme, action bar, animations, and button design
    * Started working on map pin icon

#### Day 4

#### Day 5

## Code Style
Java for Android

## Tests
Espresso tests for login, signup, home page, game page. 

## Communication plan
* We will strive to create an open and welcoming environment where participation and contribution to the project and general community is a harassment-free experience for everyone, regardless of age, body size, disability, ethnicity, sex characteristics, gender identity and expression, level of experience, education, socio-economic status, nationality, personal appearance, race, religion, or sexual identity and orientation.
* We will use welcoming and inclusive language, be respectful of differing viewpoints and experiences, gracefully accept constructive criticism, focus on what is best for the team and the community, and show empathy towards others.
* We will not tolerate unwelcome sexual attention or advances, trolling, insulting/derogatory comments, and personal or political attacks, public or private harassment, publishing others’ private information, or other conduct which could reasonably be considered inappropriate in an academic and professional setting.
* We agree to work as a team to hold each other accountable to these responsibilities and take appropriate and fair corrective action in response to any instances of unacceptable behavior. We will not retaliate against those who have a differing opinion or those who hold others accountable.
* We will work as a team to support each individual reaching their full potential.
* We will strive to make sure the team members communicate with each other regularly to keep the team running at the same pace and that everyone feels comfortable speaking up.
* We will listen to hear, not to reply, to ensure everyone's voice is heard. We will also make sure each team member agrees to the decisions made in the project before they are acted upon.
* We will create a safe environment where everyone feels comfortable speaking up by following our code of conduct and keeping each other accountable.
* Be patient and kind with each other.
* Suggestion: Start sentences with “I need...”
* There are no bad ideas.
* Go way overkill on the comments.

## Conflict plan
* If disagreements arise in the team we will make sure that everyone has a chance to state their opinion and be heard, not spoken over or dismissed, and discuss possible solutions as a team. If attempts to resolve the conflict is unsuccessful and a solution cannot be agreed upon we will escalate the discussion by reaching out to Boss and HR (Nicholas, Michelle, Jeff, and Ginger).
    * Everyone stops coding.
    * Return to communication ground rules.
    * Come to a mutual agreement without taking sides (keep user stories and scope in mind).
    * Find a compromise between the intersecting viewpoints.
    * When conflict is resolved, mandatory 10 minute break to relax.
* If a team member is not adequately contributing we will raise the concern by speaking with them individually or as a team. We will focus on how we can help them contribute more. Are they struggling with something that is keeping them from moving forward? Are there other areas of the project they feel more comfortable contributing to? Consider the needs of the individual as well as the team.
    * First step: Find out why the person is falling behind, without judgement.
    * Put out the invitation to share - if something else outside of project is going on, preventing the person from being present, etc.
    * Check in with each other on how they’re doing with a task.
* Be honest.
* Ask for help when needed.

## Work plan
* Work tasks will be sorted out at stand up and reassigned throughout the day as needed.
* Tasks will be updated and tracked in project management tool.
* Daily summaries will be added to the README.
* Code Review and daily prep at 9:00am
* Debrief before leaving for the day – 5:00pm.

## Git process
* Start in the same code base each morning.
* Pull Requests as needed (particularly for route setup) and done as a group.
* Master branch will only be working versions.
* Dev branch is where we can break things.

## Misc
* Failure will happen and it’s okay! We do this thing together.
* If we’re moving too fast: Person who is slower - speak up! Fast coders can slow down. Person who is coding fast may need to take a break and help person catch up.
* Take breaks as needed after communicating with the group

## Credits
Java 401 Instructional Team
* Michelle
* Nicholas
* Jeff
* Ginger

* Camera icon made by Pixel Buddha from Flaticon www.flaticon.com

## License
