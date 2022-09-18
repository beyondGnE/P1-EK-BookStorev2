const artContent = document.getElementById("content");
const aViewBooks = document.getElementById("viewBooks");
const aAddBook = document.getElementById("addBook");

aViewBooks.addEventListener("click", async function() {
    let response = await fetch("http://localhost:7070/books");
    response = await response.json();
    console.log(response);
    populateBooks(response);
});

aAddBook.addEventListener("click", function() {
    console.log("testing add book");
});




async function apiGetABook(id) {
    let response = await fetch("http://localhost:7070/books/"+id);
    response = await response.json();
    console.log(response);
    populateBookDetails(response);
}

// Creating book template for populating books
// Has an img link to the book, and the title of the book underneath it.
function template_BookThumb(responseItem) {
    if (responseItem != null) {
        let bookContainer = document.createElement('div');
        bookContainer.classList.add('bookThumbContainer');
        let bookLink = document.createElement('a');
        let bookImg = document.createElement('img');
        
        bookImg.src = responseItem.imgUrl;
        bookImg.alt = responseItem.title;
        bookImg.classList.add('bookThumbImg');

        bookLink.href="#";
        bookLink.classList.add('bookThumbImgLink');
        bookLink.paramIndex = responseItem.bookId;
        bookLink.addEventListener('click', function(event) {
            apiGetABook(event.currentTarget.paramIndex); // The current target in this case is the bookLink you're clicking on!
        });

        bookLink.appendChild(bookImg);

        let bookTitle = document.createElement('p');
        bookTitle.classList.add('bookThumbTitle');
        bookTitle.innerText = responseItem.title;

        bookContainer.appendChild(bookLink);
        bookContainer.appendChild(bookTitle);

        return bookContainer;
    } return null;
}

async function template_BookPage(response) {

}

// For looking at a specific book
// Sort the response's guts into their respective areas.
async function populateBooks(response) {
    // console.log(response);
    // artContent.appendChild(template_BookThumb(response[1]));
    // console.log(response[0].title);
    // console.log(template_BookThumb(response[0]));
    let bookTable = document.createElement('table');
    bookTable.classList.add('bookTable');
    for (let i = 0; i < 10; i++)
    {
        // let bookRow = document.createElement('tr');
        let bookRow = document.createElement('ul');
        bookRow.classList.add('bookRow');
        for (let j = i; j < 5; j++) {
            // let bookItem = document.createElement('td');
            let bookItem = document.createElement('li');
            bookItem.classList.add('bookItem');
            
            let bookThumb = template_BookThumb(response[i+j]);
            if (bookThumb != null) {
                bookItem.appendChild(bookThumb);
                bookRow.appendChild(bookItem);
            }
            // console.log(template_BookThumb(response[i+j]));
            
        }
        if (bookRow.hasChildNodes()) {
            bookTable.appendChild(bookRow);
        }
    }
    // for (let i = 0; i < response.length; i++)
    // {
    //     let bookRow = document.createElement('tr');
    //     bookRow.classList.add('bookRow');
    //     let bookItem = document.createElement('td');
    //     bookItem.classList.add('bookItem');
    //     console.log(response[i]);
    //     bookItem.appendChild(template_BookThumb(response[i]));
    //     bookRow.appendChild(bookItem);
    //         // console.log(template_BookThumb(response[i+j]));
    //     bookTable.appendChild(bookRow);
    // }
    artContent.innerHTML = "";
    artContent.appendChild(bookTable);
}

function populateBookDetails(response) {
    let bookTitle = createBookTitle(response.title);
    let bookAuthors = createAuthorDiv(response.authors);
    let bookImg = createBookImg(response.imgUrl);

    artContent.innerText = "";
    artContent.appendChild(bookImg);
    artContent.appendChild(bookTitle);
    artContent.appendChild(bookAuthors);
}

function createBookTitle(title) {
    let h2BookTitle = document.createElement('h2');
    h2BookTitle.classList.add('book-title-details');
    h2BookTitle.innerText = title;
    return h2BookTitle;
}

function createAuthorDiv(authorList) {
    let divAuthors = document.createElement('p');
    divAuthors.classList.add('book-authors-details');
    divAuthors.innerText = "by ";
    for (let i = 0; i < authorList.length; i++) {
        authorName = authorList[i].firstName + " " + authorList[i].lastName;
        if (i === authorList.length-1) {
            divAuthors.innerText += authorName;
        } else {
            divAuthors.innerText += authorName + ", ";
        }
    };

    return divAuthors;
}

function createBookImg(imgUrl) {
    let asideImg = document.createElement('aside');
    asideImg.classList.add('asideImg');
    let actualImg = document.createElement('img');
    actualImg.classList.add('book-img-details');
    actualImg.src = imgUrl;

    asideImg.appendChild(actualImg);
    return asideImg;
}

async function apiPostBook() {
    titleInput = document.getElementById('titleInput');
    authorFirstName = document.getElementById('authorFirstName');

    let inputBook = {
        title: titleInput.value != null ? titleInput.value : "",
        author: authorFirstName.value != null ? titleInput.value : "",
    }
}