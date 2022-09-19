const artContent = document.getElementById("content");
const aViewBooks = document.getElementById("viewBooks");
const aAddBook = document.getElementById("addBook");

aViewBooks.addEventListener("click", async function() {
    let response = await fetch("http://localhost:7070/api/books");
    response = await response.json();
    console.log(response);
    populateBooks(response);
});

aAddBook.addEventListener("click", function() {
    artContent.innerHTML = "";
    let arrLabels = ['title', 'price', 'quantity', 'isbn', 'publishDate'];
    arrLabels.forEach(function(label) {
        let aSection = document.createElement('div');
        let aLabel = document.createElement('label');
        aLabel.innerText = label + ": ";
        let aInput = document.createElement('input');
        aInput.classList.add(label+'Input');
        aInput.id = label+'Input';
        aSection.appendChild(aLabel);
        aSection.appendChild(aInput);

        artContent.appendChild(aSection);
    });

    let aSection = document.createElement('div');
    let firstNameLabel = document.createElement('label');
    firstNameLabel.innerText = "author first name: ";
    let firstNameInput = document.createElement('input');
    firstNameInput.classList.add('firstNameInput');
    firstNameInput.id = 'firstNameInput';

    let lastNameLabel = document.createElement('label');
    lastNameLabel.innerText = "author last name: ";
    let lastNameInput = document.createElement('input');
    lastNameInput.classList.add('lastNameInput');
    lastNameInput.id = 'lastNameInput';
    aSection.appendChild(firstNameLabel);
    aSection.appendChild(firstNameInput);
    aSection.appendChild(lastNameLabel);
    aSection.appendChild(lastNameInput);

    artContent.appendChild(aSection);

    let submitButton = document.createElement('button');
    submitButton.innerText = "Submit";
    submitButton.addEventListener('click', async function() {
    
        let inputBook = {
            title: document.getElementById('titleInput').value != null ? document.getElementById('titleInput').value : "",
            price: document.getElementById('priceInput').value != null ? document.getElementById('priceInput').value : "",
            quantity: document.getElementById('quantityInput').value != null ? document.getElementById('quantityInput').value : "",
            imgUrl: "",
            isbn: document.getElementById('isbnInput').value != null ? document.getElementById('isbnInput').value : "",
            publishDate: document.getElementById('publishDateInput').value != null ? document.getElementById('publishDateInput').value : "",
            authors: [{
                        firstName: document.getElementById('firstNameInput').value,
                        lastName: document.getElementById('lastNameInput').value 
                    }],
            genres: []
        };
        let response = await fetch('http://localhost:7070/api/books', {
            method: 'POST',
            mode: 'cors',
            headers: {
                'Content-Type': 'application/json'
            },
            body: JSON.stringify(inputBook)
        });
    });
    artContent.appendChild(submitButton);
});

async function apiGetABook(id) {
    let response = await fetch("http://localhost:7070/api/books/"+id);
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
            // console.log(event.currentTarget.paramIndex);
            apiGetABook(event.currentTarget.paramIndex); // The current target in this case is the bookLink you're clicking on!
            // apiGetABook(1);
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

async function editBookPage(response) {

}

// For looking at a specific book
// Sort the response's guts into their respective areas.
async function populateBooks(response) {

    let bookTable = document.createElement('table');
    bookTable.classList.add('bookTable');
    for (let i = 0; i < 10; i+=5)
    {
        // let bookRow = document.createElement('tr');
        let bookRow = document.createElement('ul');
        bookRow.classList.add('bookRow');
        for (let j = 0; j < 5; j++) {
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

    artContent.innerHTML = "";
    artContent.appendChild(bookTable);
}

function populateBookDetails(response) {
    let bookTitle = createBookTitle(response.title);
    let bookAuthors = createAuthorDiv(response.authors);
    let bookImg = createBookImg(response.imgUrl);
    let editButton = document.createElement('button');
    editButton.classList.add('editButton');
    editButton.innerText = "Edit";
    editButton.addEventListener('click', function() {
        
        artContent.innerHTML = "";
        let arrLabels = ['title', 'price', 'quantity', 'isbn', 'publishDate', 'author'];
        arrLabels.forEach(function(label) {
            let aSection = document.createElement('div');
            let aLabel = document.createElement('label');
            aLabel.innerText = label + ": ";
            let aInput = document.createElement('input');
            aInput.classList.add(label+'Input');
            aInput.id = label+'Input';
            aSection.appendChild(aLabel);
            aSection.appendChild(aInput);

            artContent.appendChild(aSection);
        });

        let aSection = document.createElement('div');
        let firstNameLabel = document.createElement('label');
        firstNameLabel.innerText = "author first name: ";
        let firstNameInput = document.createElement('input');
        firstNameInput.classList.add('firstNameInput');
        firstNameInput.id = 'firstNameInput';

        let lastNameLabel = document.createElement('label');
        lastNameLabel.innerText = "author last name: ";
        let lastNameInput = document.createElement('input');
        lastNameInput.classList.add('lastNameInput');
        lastNameInput.id = 'lastNameInput';
        aSection.appendChild(firstNameLabel);
        aSection.appendChild(firstNameInput);
        aSection.appendChild(lastNameLabel);
        aSection.appendChild(lastNameInput);

        artContent.appendChild(aSection);

        let submitButton = document.createElement('button');
        submitButton.innerText = "Submit";
        submitButton.addEventListener('click', async function() {
        
            let inputBook = {
                title: document.getElementById('titleInput').value != "" ? document.getElementById('titleInput').value : response.title,
                price: document.getElementById('priceInput').value != "" ? document.getElementById('priceInput').value : response.price,
                quantity: document.getElementById('quantityInput').value != "" ? document.getElementById('quantityInput').value : response.quantity,
                imgUrl: "",
                isbn: document.getElementById('isbnInput').value != "" ? document.getElementById('isbnInput').value : response.isbn,
                publishDate: document.getElementById('publishDateInput').value != "" ? document.getElementById('publishDateInput').value : response.publishDate,
                authors: [
                            {
                                firstName: document.getElementById('firstNameInput').value,
                                lastName: document.getElementById('lastNameInput').value
                            }],
                genres: []
            };
            let toUpdate = await fetch('http://localhost:7070/api/books/'+response.bookId, {
                method: 'PUT',
                mode: 'cors',
                headers: {
                    'Content-Type': 'application/json'
                },
                body: JSON.stringify(inputBook)
            });
            console.log(inputBook);
            populateBooks();
        });
        
        artContent.appendChild(submitButton);
    });
    let deleteButton = document.createElement('button');
    deleteButton.classList.add('deleteButton');
    deleteButton.innerText = "Delete";
    deleteButton.addEventListener('click', async function() {
        let toDelete = await fetch('http://localhost:7070/api/books/'+response.bookId, {
            method: 'DELETE',
            mode: 'cors',
            headers: {
                'Content-Type': 'application/json'
            },
            body: response
        });
    });


    artContent.innerText = "";
    artContent.appendChild(bookImg);
    artContent.appendChild(bookTitle);
    artContent.appendChild(bookAuthors);
    artContent.appendChild(editButton);
    artContent.appendChild(deleteButton);
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
    
}
