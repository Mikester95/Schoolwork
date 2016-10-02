// We want to dropdown the dropdownContent upon mouseover on the lessonContentHeader.
// We want to drag back up the list upon mouseout on the lesson content.
var lessonContent = document.getElementsByClassName('lessonContent')[0];
var dropdownContent = document.getElementsByClassName('dropdownContent')[0];

// Time of the last showing.
var last_showing = 0;

lessonContent.onmouseover = function() {
	last_showing = Date.now();
	// Add the dropdownContent to the DOM.
	dropdownContent.setAttribute('class', 'dropdownContent dropdownContentDisplay dropdownContentHiddenAnimate');
	// After it's been added, animate it to dropdown.
	setTimeout(function() {
		dropdownContent.setAttribute('class', 'dropdownContent dropdownContentDisplay dropdownContentDisplayAnimate');
	}, 0);
}

lessonContent.onmouseout = function() {
	// Animate the dropdownContent to drag up and hide.
	dropdownContent.setAttribute('class', 'dropdownContent dropdownContentDisplay dropdownContentHiddenAnimate');
	// In one second, if there hasn't been another mouseover event on the lesson content in the meantime, 
        // remove the dropdownContent from the DOM. This is to free up the space on the page.
	setTimeout(function() {
		if (Date.now() - last_showing >= 1000) {
			dropdownContent.setAttribute('class', 'dropdownContent dropdownContentHidden dropdownContentHiddenAnimate');
		}
	}, 1000);
}