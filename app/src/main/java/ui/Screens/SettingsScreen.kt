package ui.Screens

import android.net.Uri
import android.util.Log
import android.widget.Toast
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.PickVisualMediaRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import coil.compose.AsyncImage
import com.calculator.bookfinder.accountbuttons.AccountButtons
import com.calculator.bookfinder.accountbuttons.lindenHill

import com.calculator.bookfinder.header.Header
import com.calculator.bookfinder.morebuttons.MoreButtons
import com.calculator.bookfinder.naviagtionbar.NaviagtionBar
import com.calculator.bookfinder.postheader.PostHeader
import com.calculator.bookfinder.postheader.Property1
import com.calculator.bookfinder.userpfp.AddProfilePictureButtonProperty1Variant2
import com.calculator.bookfinder.userpfp.BlankPfpProperty1Variant2
import com.calculator.bookfinder.userpfp.TopLevelProperty1Variant2
import com.calculator.bookfinder.userpfp.VectorProperty1Variant2
import com.google.relay.compose.BoxScopeInstance.columnWeight
import com.google.relay.compose.BoxScopeInstance.rowWeight
import data.Routes.Routes
import ui.ViewModel.BookDatabaseViewModel
import ui.ViewModel.BookViewModel
import ui.ViewModel.LoginViewModel
import ui.ViewModel.UserInteractionViewmodel
/**
 * Composable function that displays the Settings screen, allowing users to update their profile information.
 *
 * @param userInteractionViewmodel ViewModel for handling user interactions and data updates.
 * @param loginViewModel ViewModel for handling login-related logic (used for sign-out).
 * @param navController Navigation controller for navigating between screens.
 */
@Composable
fun SettingsScreen(userInteractionViewmodel: UserInteractionViewmodel, loginViewModel: LoginViewModel, navController: NavController){
    var forcedRefresh by remember { mutableIntStateOf(0) }
    val userName by remember { mutableStateOf(userInteractionViewmodel.myUserName()) } // Current username
    var oldFullName by remember { mutableStateOf("") } // State for updating full name
    var oldDescription by remember { mutableStateOf("") } // State for updating description
    var dialogOpen by remember { mutableStateOf(false) } // State for sign-out dialog visibility
    var displayMessage by remember { mutableStateOf(false) } // State for displaying update success message
    var infoPlaceholder by remember {
        mutableStateOf(mutableListOf<String>())
    } // Placeholder for initial user info
    var friendNumber by remember {
        mutableIntStateOf(0)
    } // Number of friends
    var postNumber by remember {
        mutableIntStateOf(0)
    } // Number of posts

    userInteractionViewmodel.getSelectedUserInfo(userName,name={
        infoPlaceholder.add(it)
    },friends={},pfp={})

    Text(text = forcedRefresh.toString())
    Column (modifier= Modifier
        .fillMaxSize()
        .background(color = Color(0xFFFFFFFF)),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Top){
        Column(
            modifier = Modifier
                .fillMaxSize()){
            PostHeader(
                Modifier
                    .fillMaxWidth()
                    .fillMaxHeight(0.07f),
                property1 = Property1.Variant2,
                backButton = {
                    navController.popBackStack()
                },
                text = "Settings",
                logOutButton = {
                    dialogOpen = true
                })
            Row(
                modifier = Modifier
                    .padding(top = 40.dp)
                    .fillMaxWidth(),horizontalArrangement = Arrangement.Center) {
                UserProfilePicture(modifier = Modifier
                    .height(147.dp)
                    .width(147.dp), changePfpButton = {}, userInteractionViewmodel = userInteractionViewmodel,userName,navController)
            }
            // Fetch user stats (friends and posts)
            userInteractionViewmodel.getUserStats(
                friends = {
                    friendNumber = it
                },
                posts = {
                    postNumber = it
                }
            )
            // Display user stats (posts, friends, groups)
            Row(modifier = Modifier
                .padding(top = 30.dp)
                .fillMaxWidth(),
                horizontalArrangement = Arrangement.Start) {
                Spacer(modifier = Modifier.fillMaxWidth(0.05f))
                Column(horizontalAlignment = Alignment.CenterHorizontally){
                    Text(text = "Posts",fontFamily = lindenHill, fontSize = 23.sp)
                    Text(text = postNumber.toString(),fontFamily = lindenHill, fontSize = 23.sp)
                }
                Spacer(modifier = Modifier.fillMaxWidth(0.3f))
                Column(horizontalAlignment = Alignment.CenterHorizontally){
                    Text(text = "Friends",fontFamily = lindenHill, fontSize = 23.sp)
                    Text(text = friendNumber.toString(),fontFamily = lindenHill, fontSize = 23.sp)
                }
                Spacer(modifier = Modifier.fillMaxWidth(0.47f))
                Column(horizontalAlignment = Alignment.CenterHorizontally){
                    Text(text = "Groups",fontFamily = lindenHill, fontSize = 23.sp)
                    Text(text = "0",fontFamily = lindenHill, fontSize = 23.sp)
                }
            }


            Row(modifier = Modifier.fillMaxWidth(),){
                Spacer(modifier = Modifier.fillMaxWidth(0.05f))
                Column (
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = 30.dp),
                    horizontalAlignment = Alignment.Start){

                    Text(text = "Name",fontFamily = lindenHill, fontSize = 23.sp)
                    TextField(
                        modifier = Modifier.fillMaxWidth(0.94f),
                        value = oldFullName ,

                        onValueChange ={
                            oldFullName = it
                        } ,
                        placeholder = {
                            if (infoPlaceholder.isNotEmpty()){
                                Text(text = infoPlaceholder[0],fontFamily = lindenHill,modifier = Modifier)
                            }
                        })
                }
            }
            Row(modifier = Modifier.fillMaxWidth(),){
                Spacer(modifier = Modifier.fillMaxWidth(0.05f))
                Column (
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = 30.dp),
                    horizontalAlignment = Alignment.Start){

                    Text(text = "About",fontFamily = lindenHill, fontSize = 23.sp)
                    TextField(
                        modifier = Modifier
                            .fillMaxWidth(0.94f)
                            .fillMaxHeight(0.4f),
                        value = oldDescription ,
                        onValueChange ={
                            oldDescription = it
                        } )
                }
            }
            Row(modifier = Modifier
                .padding(top = 60.dp)
                .fillMaxWidth(),
                horizontalArrangement = Arrangement.Center){
                AccountButtons(
                    buttonPressed = {
                        userInteractionViewmodel.updateUserInfo(oldFullName,oldDescription,
                            succes = {
                                if (it){
                                    displayMessage = it
                                }
                            })
                    },
                    buttonName = "Sign out",
                    property1 = com.calculator.bookfinder.accountbuttons.Property1.Variant4,
                    addRemoveRequest = "Update Profile",
                    modifier = Modifier
                        .rowWeight(1.0f)
                        .columnWeight(1.0f)
                        .height(55.dp)
                        .fillMaxWidth(0.9f)
                )
            }


        }
        if (dialogOpen){
            SignOutDialog(
                dialogClose = {
                    dialogOpen = false
                },navController,loginViewModel
            )
        }
    }
    if (displayMessage){
        Message(text = "Profile Updated")
    }

    forcedRefresh += 1
    forcedRefresh -= 1
}


/**
 * Composable function that loads and displays a user's profile picture from Firebase.
 *
 * @param userInteractionViewmodelViewModel for handling user interactions and image retrieval.
 * @param userName The username of the user whose profile picture is to be loaded.
 */
@Composable
fun LoadPfp(userInteractionViewmodel: UserInteractionViewmodel, userName:String){
    val resetPfp by  userInteractionViewmodel.resetPfp.collectAsState() // State for resetting the profile picture
    var oldImageUri by remember { mutableStateOf<Uri?>(null) } // State for storing the image URI

    // Fetch image URI from Firebase
    userInteractionViewmodel.getImageFromFirebase(imageUri = {
        oldImageUri = it // Update image URI state
        userInteractionViewmodel.checkUri(it) // Check if URI is valid
    },userName)

    // Conditionally display loading indicator or image
    if (oldImageUri == null || resetPfp){ // Show loading indicatorwhile image is being fetched or reset
        Column (modifier= Modifier.fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center){
            Loading(height = 50, width = 50)
        }
    }else{
        AsyncImage(model =oldImageUri , contentDescription ="test" ,modifier= Modifier.fillMaxSize(),contentScale = ContentScale.Crop) // Display image once loaded
    }
}

/**
 * Composable function that displays a toast message.
 *
 * @param text The text message to display in the toast.
 */
@Composable
fun Message(text:String){
    Toast.makeText(LocalContext.current,text, Toast.LENGTH_SHORT).show()
}


/**
 * Composable function that displays the user's profile picture and a button to change it.
 *
 * @param modifier Modifierfor the overall layout.
 * @param changePfpButton Callback function to trigger the profile picture change process.
 * @param userInteractionViewmodel ViewModel for handling user interactions, including image uploads.
 * @param userName The username of the user whose profile picture is displayed.
 * @param navController Navigationcontroller (unused in this composable).
 */
@Composable
fun UserProfilePicture(modifier: Modifier, changePfpButton:()->Unit, userInteractionViewmodel: UserInteractionViewmodel, userName:String,navController: NavController){
    val launcher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.PickVisualMedia(),
        onResult = {
            userInteractionViewmodel.resetPfpValue()
            userInteractionViewmodel.uploadImageToFirebase(it,1)
        })
    TopLevelProperty1Variant2(modifier = modifier) {
        BlankPfpProperty1Variant2(
            modifier = Modifier
                .rowWeight(1.0f)
                .columnWeight(1.0f)
                .background(
                    Color(0x0AFFFFFF)
                )
        ) {
            LoadPfp(userInteractionViewmodel, userName )
        }
        AddProfilePictureButtonProperty1Variant2(
            changePfpButton = {

                changePfpButton()
                launcher.launch(
                    PickVisualMediaRequest(ActivityResultContracts.PickVisualMedia.ImageOnly)

                )

            },
            modifier = Modifier
                .rowWeight(1.0f)
                .columnWeight(1.0f)
        ) {
            VectorProperty1Variant2(
                modifier = Modifier
                    .rowWeight(1.0f)
                    .columnWeight(1.0f)
            )
        }
    }
}



/**
 * Composable function that displays a confirmation dialog for signing out.
 *
 * @param dialogClose Callback function to close the dialog.
 * @param navController Navigation controller for navigating to the Login screen.
 * @param loginViewModel ViewModel for handling login-related logic (unused in this composable).
 */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SignOutDialog(dialogClose:()->Unit,navController: NavController,loginViewModel: LoginViewModel){

    AlertDialog(
        onDismissRequest = { dialogClose() },
        modifier = Modifier
            .background(Color(0xC2C1BF))

    ) {
        com.calculator.bookfinder.removefrienddialog.RemoveFriendDialog(
            textButton = "Are you sure you want to sign out?",
            removeButton = {
                dialogClose()
                navController.navigate(Routes.LoginScreen.route)

            },
            cancelButton = {
                dialogClose()
            },
            modifier = Modifier
                .height(184.dp)
                .width(330.dp),
            text = "Sign Out"
        )
    }


}


