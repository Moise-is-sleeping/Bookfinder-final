package ui.Screens

import android.net.Uri
import android.util.Log
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.requiredHeight
import androidx.compose.foundation.layout.requiredWidth
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Cancel
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material.icons.filled.KeyboardControlKey
import androidx.compose.material.icons.filled.ModeComment
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.outlined.ChatBubbleOutline
import androidx.compose.material.icons.outlined.Lock
import androidx.compose.material.icons.outlined.ModeComment
import androidx.compose.material.icons.outlined.Star
import androidx.compose.material3.Divider
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuDefaults.textFieldColors
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.IconToggleButton
import androidx.compose.material3.InputChip
import androidx.compose.material3.InputChipDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.rememberVectorPainter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.DpOffset
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import coil.compose.AsyncImage
import com.calculator.bookfinder.R
import com.calculator.bookfinder.accountbuttons.AccountButtons
import com.calculator.bookfinder.accountbuttons.Property1
import com.calculator.bookfinder.accountbuttons.lindenHill
import com.calculator.bookfinder.addpicture.AddPicture
import com.calculator.bookfinder.header.lancelot
import com.calculator.bookfinder.postheader.PostHeader
import com.calculator.bookfinder.reviewpost.Class10Jan2024
import com.calculator.bookfinder.reviewpost.Deadpool1
import com.calculator.bookfinder.reviewpost.Frame19
import com.calculator.bookfinder.reviewpost.HomepageBooks
import com.calculator.bookfinder.reviewpost.Line3
import com.calculator.bookfinder.reviewpost.Moisebrenes
import com.calculator.bookfinder.reviewpost.More
import com.calculator.bookfinder.reviewpost.UserInstance
import com.calculator.bookfinder.reviewpost.Vector1
import com.calculator.bookfinder.reviewpostvariant2.Date
import com.calculator.bookfinder.reviewpostvariant2.Description
import com.calculator.bookfinder.reviewpostvariant2.Title
import com.calculator.bookfinder.reviewpostvariant2.Username
import com.calculator.bookfinder.tagbook.TagBook
import com.google.relay.compose.BoxScopeInstance.columnWeight
import com.google.relay.compose.BoxScopeInstance.rowWeight
import com.google.relay.compose.RelayContainer
import com.google.relay.compose.RelayContainerScope
import ui.ViewModel.BookDatabaseViewModel
import ui.ViewModel.BookViewModel
import ui.ViewModel.PostsGroupsViewmodel

@OptIn(ExperimentalMaterial3Api::class)

@Composable
fun PostScreen(bookDatabaseViewModel: BookDatabaseViewModel, navController: NavController, postsGroupsViewmodel: PostsGroupsViewmodel, bookViewModel: BookViewModel){
    var postTitle by remember {
        mutableStateOf("")
    }
    var postDescription by remember {
        mutableStateOf("")
    }
    val starColors by postsGroupsViewmodel.starColor.collectAsState()
    var tagBook by remember { mutableStateOf(false)}
    val searchValue by bookViewModel.searchValue.collectAsState()
    val hasSearched by bookViewModel.hasSearched.collectAsState()
    val book by postsGroupsViewmodel.book.collectAsState()
    var message by remember {
        mutableStateOf(false)
    }




    Column(modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally){
        if (!tagBook){
            PostHeader(
                Modifier
                    .fillMaxWidth()
                    .fillMaxHeight(0.07f),
                backButton = {
                    navController.popBackStack()
                },
                property1 = com.calculator.bookfinder.postheader.Property1.Default,
                text = "Posts")
            Spacer(modifier = Modifier.height(30.dp))
            TextField(
                modifier = Modifier.fillMaxWidth(),
                value = postTitle ,
                onValueChange ={
                    postTitle = it
                },
                placeholder ={
                    Text(text = "Add a title")
                },
                colors = textFieldColors(
                    focusedContainerColor = Color(0xFFE6E5E5),
                    unfocusedContainerColor = Color.Transparent,
                    cursorColor = Color.Black,
                    focusedIndicatorColor = Color.Black,
                    unfocusedIndicatorColor = Color.Black
                )
            )
            Spacer(modifier = Modifier.height(10.dp))
            TextField(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(300.dp),
                value = postDescription ,
                onValueChange ={
                    postDescription = it
                },
                placeholder ={
                    Text(text = "Add a Description")
                },
                colors = textFieldColors(
                    focusedContainerColor = Color(0xFFE6E5E5),
                    unfocusedContainerColor = Color.Transparent,
                    cursorColor = Color.Black,
                    focusedIndicatorColor = Color.Transparent,
                    unfocusedIndicatorColor = Color.Transparent
                )
            )
            Column(modifier = Modifier.padding(start = 15.dp)) {
                Text(text = "Rating", fontFamily = lancelot, fontSize = 23.sp,
                    color = Color(
                        alpha = 255,
                        red = 0,
                        green = 0,
                        blue = 0
                    )
                )
                Row(modifier = Modifier.fillMaxWidth()) {
                    for (i in 1..5){
                        IconToggleButton(checked = false, onCheckedChange = {
                            postsGroupsViewmodel.starRatings(i)
                            postsGroupsViewmodel.starColorPicker()
                        }) {
                            Icon(
                                modifier = Modifier
                                    .size(48.dp),
                                imageVector = Icons.Outlined.Star,
                                contentDescription = "Localized description",
                                tint = Color(starColors[i-1])
                            )
                        }
                    }
                }
            }
            Spacer(modifier = Modifier.height(10.dp))
            Row (modifier = Modifier
                .fillMaxWidth()
                .padding()
                ,horizontalArrangement = Arrangement.Center){
                if(book.title == ""){
                    TagBook(
                        tagButton = {
                            tagBook = true
                        },
                        modifier = Modifier
                            .rowWeight(1.0f)
                            .columnWeight(1.0f)
                            .height(42.dp)
                            .width(167.dp)
                    )
                }else{
                    InputChip(
                        selected = tagBook,
                        onClick = {
                            tagBook = !tagBook
                                  },
                        label = {
                            Text(postsGroupsViewmodel.bookNameLength(book.title), fontFamily = lancelot, fontSize = 23.sp)
                                },
                        trailingIcon = {
                            Icon(
                                Icons.Filled.Cancel,
                                contentDescription = "Localized description",
                                Modifier.size(InputChipDefaults.IconSize)
                            )
                        }
                    )
                }
                Spacer(modifier = Modifier.fillMaxWidth(0.6f))
                AddPicture(
                    addPicture = {},
                    modifier = Modifier
                        .rowWeight(1.0f)
                        .columnWeight(1.0f)
                        .height(42.dp)
                        .width(48.dp)
                )

            }
            Spacer(modifier = Modifier.fillMaxHeight(0.15f))
            AccountButtons(
                modifier = Modifier
                    .fillMaxWidth(0.9f)
                    .height(42.dp),
                property1 = Property1.Variant5,
                buttonName = "Create Post",
                buttonPressed = {
                    postsGroupsViewmodel.uploadPost(postTitle,postDescription,book, "", succes = {
                        message = true
                        navController.popBackStack()
                    })
                }
            )
        }
        else{
            PostHeader(
                Modifier
                    .fillMaxWidth()
                    .fillMaxHeight(0.07f),
                backButton = {
                   tagBook = false
                },
                property1 = com.calculator.bookfinder.postheader.Property1.Default,
                text = "Tag Book")
            Spacer(modifier = Modifier.padding(top = 30.dp))
            SearchBar(modifier = Modifier
                .height(60.dp)
                .width(390.dp)
                .rowWeight(1.0f)
                .columnWeight(1.0f)
                ,
                searchButton = {
                    bookViewModel.searchBooksByName(searchValue)
                    bookViewModel.hasSearched()
            },
                bookViewModel )
            if (hasSearched){
                Searchresults(2, book = {
                    postsGroupsViewmodel.setBook(it)
                    tagBook = false
                },bookDatabaseViewModel,bookViewModel,navController)

            }
        }


    }
    if (message){
        Message("Post Created")
        message = false
    }

}


@Composable
fun UserPost(name:String, date:String, title:String, description:String, rating:Int,likes:Int, comments:Int){
    var icontype by remember {
        mutableStateOf(false)
    }

    Column(modifier = Modifier
        .background(Color.White)
        .padding(start = 10.dp, end = 10.dp)
        .fillMaxWidth()){
        UserPostHeader(name,date)
        Row (modifier = Modifier
            .background(Color.Black)
            .height(1.dp)
            .fillMaxWidth()){
        }
        Text(text = title, fontFamily = lindenHill, fontSize = 25.sp,
            color = Color(
                alpha = 255,
                red = 0,
                green = 0,
                blue = 0
            ),modifier = Modifier.padding(top = 15.dp,bottom = 15.dp, start = 5.dp))
        Text(text = description, fontFamily = lindenHill, fontSize = 18.sp,
            color = Color(
                alpha = 255,
                red = 0,
                green = 0,
                blue = 0
            ),modifier = Modifier.padding(top = 5.dp,bottom = 15.dp, start = 5.dp))
        Row(modifier = Modifier.fillMaxWidth()) {
            Text(text = "Rating", fontFamily = lindenHill, fontSize = 20.sp,
                color = Color(
                    alpha = 255,
                    red = 0,
                    green = 0,
                    blue = 0
                ),modifier = Modifier.padding(start = 5.dp,top = 7.dp, end = 10.dp))
            Row(modifier = Modifier.fillMaxWidth(0.8f)) {
                for (i in 1..rating){
                    Icon(
                        modifier = Modifier
                            .size(40.dp),
                        imageVector = Icons.Outlined.Star,
                        contentDescription = "Localized description",
                        tint = Color(0xFFF7F772)
                    )
                }
            }
            DropDownInfo( buttonPressed = {
                icontype = !icontype
            })
        }
        if (icontype){
            Row (modifier = Modifier.padding(bottom = 27.dp)){
                BooksEdit(
                    modifier = Modifier
                        .height(95.dp)
                        .width(70.dp)
                        .padding(start = 7.dp)
                        .offset(y = 7.dp)
                ) {
                    //if the function returns empty, the place holder for the cover photo is left
/*                    if (bookDatabaseViewModel.gotCovers(it) != "empty"){
                        AsyncImage(
                            model = "https://covers.openlibrary.org/b/id/${it.covers?.get(0)}-M.jpg",
                            contentDescription = "test",
                            modifier = Modifier.fillMaxSize()
                        )
                    }*/
                }
            }
        }
        Row (modifier = Modifier
            .background(Color.Black)
            .height(1.dp)
            .fillMaxWidth()){
        }
        Row {
            Column(modifier = Modifier
                .padding(end = 5.dp)) {
                IconButton(onClick = {

                }) {
                    Icon(
                        Icons.Filled.Favorite,
                        contentDescription = "Localized description",
                        Modifier.size(42.dp)
                    )
                }
            }
            Column (modifier = Modifier
                .padding(start = 5.dp)){
                IconButton(onClick = {

                }) {
                    Image(modifier = Modifier.size(34.dp), painter = painterResource(id = R.drawable.postcomments), contentDescription ="" )
                }
            }
        }

    }



}


@Composable
fun UserPostHeader(name:String, date: String){
    Row(modifier = Modifier

        .fillMaxWidth()
        ) {
        HeaderContainer(
            modifier = Modifier
                .rowWeight(1.0f)
                .columnWeight(1.0f)
                .height(65.dp)
        ) {
            Username(
                username = name,
                modifier = Modifier.boxAlign(
                    alignment = Alignment.TopStart,
                    offset = DpOffset(
                        x = 56.0.dp,
                        y = 13.0.dp
                    )
                )
            )
            com.calculator.bookfinder.reviewpostvariant2.More(
                moreButton2 = { },
                modifier = Modifier.boxAlign(
                    alignment = Alignment.TopEnd,
                    offset = DpOffset(
                        x = -6.0.dp,
                        y = 21.0.dp
                    )
                )
            ) {
                com.calculator.bookfinder.reviewpostvariant2.Vector1(
                    modifier = Modifier
                        .rowWeight(
                            1.0f
                        )
                        .columnWeight(1.0f)
                )
            }
            Date(
                date = date,
                modifier = Modifier.boxAlign(
                    alignment = Alignment.TopStart,
                    offset = DpOffset(
                        x = 57.0.dp,
                        y = 32.0.dp
                    )
                )
            )
            Log.d("dates",date)
            com.calculator.bookfinder.reviewpostvariant2.Line3(
                modifier = Modifier.boxAlign(
                    alignment = Alignment.BottomEnd,
                    offset = DpOffset(
                        x = -6.5.dp,
                        y = -2.5.dp
                    )
                )
            )
            com.calculator.bookfinder.reviewpostvariant2.HomepageBooks(
                modifier = Modifier.boxAlign(
                    alignment = Alignment.TopStart,
                    offset = DpOffset(
                        x = 7.0.dp,
                        y = 11.0.dp
                    )
                )
            ) {
                com.calculator.bookfinder.reviewpostvariant2.Deadpool1(
                    modifier = Modifier.boxAlign(
                        alignment = Alignment.TopStart,
                        offset = DpOffset(
                            x = 3.0.dp,
                            y = 0.0.dp
                        )
                    )
                )
            }
        }
    }
}

@Composable
fun HeaderContainer(
    modifier: Modifier = Modifier,
    content: @Composable RelayContainerScope.() -> Unit
) {
    RelayContainer(
        isStructured = false,
        content = content,
        modifier = modifier
            .padding(
                paddingValues = PaddingValues(
                    start = 0.0.dp,
                    top = 0.0.dp,
                    end = 0.0.dp,
                    bottom = 0.0.dp
                )
            )
            .fillMaxWidth(1.0f)
            .fillMaxHeight(1.0f)
    )
}


@Composable
fun DropDownInfo(buttonPressed:()->Unit){
    var icon by remember {
        mutableStateOf(Icons.Filled.KeyboardArrowDown)
    }
    IconButton(onClick = {
        if (icon == Icons.Filled.KeyboardArrowDown){
            icon = Icons.Filled.KeyboardControlKey
        }else{
            icon = Icons.Filled.KeyboardArrowDown
        }
        buttonPressed()
    }) {
        Icon(
            icon,
            contentDescription = "Localized description",
            Modifier.size(38.dp)
        )
    }
}