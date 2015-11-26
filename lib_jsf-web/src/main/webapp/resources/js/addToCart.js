function addToCart(title, copies, amount) {
    if (amount >= copies) {
        alert('Book ' + title + ' not added to cart. No exemplars of this book in library')
    } else {
        alert('Book ' + title + ' added to cart.\nAmount: ' + (amount + 1) + ' exemplars');
    }
}