$(function () {

	
	$('.flexslider').flexslider({
		slideshow: false,
		slideshowSpeed: 5000,
		pauseOnHover: true,
		start: renderPreview,	//render preview on start
		before: renderPreview //render preview before moving to the next slide
	});
		
	function renderPreview(slider) {
	
		 var sl = $(slider);
		 var prevWrapper = sl.find('.flex-prev');
		 var nextWrapper = sl.find('.flex-next');		 
		 
		 //calculate the prev and curr slide based on current slide
		 var curr = slider.animatingTo;
		 var prev = (curr == 0) ? slider.count - 1 : curr - 1;
		 var next = (curr == slider.count - 1) ? 0 : curr + 1;		 

		 //add prev and next slide details into the directional nav
		 prevWrapper.find('.preview, .arrow').remove();
		 nextWrapper.find('.preview, .arrow').remove();		 
		 prevWrapper.append(grabContent(sl.find('li:eq(' + prev + ') img')));
		 nextWrapper.append(grabContent(sl.find('li:eq(' + next + ') img')));		 

	}
	
	// grab the data and render in HTML
	function grabContent(img) {		
		var tn = img.data('thumbnail');
		var alt = img.prop('alt');		
		var html = '';
		
		//you can edit this markup to your own needs, but make sure you style it up accordingly
		html = '<div class="arrow"></div><div class="preview"><img src="' + tn + '" alt="" /><div class="alt">' + alt + '</div></div>';	
		return html;
	}
	
});