/**
 * Greedy Nav: moves nav items that don't fit into a hamburger dropdown.
 */
document.addEventListener('DOMContentLoaded', function() {
  var nav = document.querySelector('.greedy-nav');
  if (!nav) return;
  var visibleLinks = nav.querySelector('.visible-links');
  var hiddenLinks = nav.querySelector('.hidden-links');
  var toggleBtn = nav.querySelector('.greedy-nav__toggle');
  if (!visibleLinks || !hiddenLinks || !toggleBtn) return;

  // Toggle dropdown
  toggleBtn.addEventListener('click', function(e) {
    e.stopPropagation();
    hiddenLinks.classList.toggle('hidden');
  });
  document.addEventListener('click', function(e) {
    if (!hiddenLinks.classList.contains('hidden') && !nav.contains(e.target)) {
      hiddenLinks.classList.add('hidden');
    }
  });

  // Record natural widths of all items before any get hidden
  var allItems = Array.from(visibleLinks.querySelectorAll(':scope > li'));
  var itemWidths = allItems.map(function(li) { return li.getBoundingClientRect().width; });

  function updateNav() {
    // Move all items back to visible first
    while (hiddenLinks.firstChild) {
      visibleLinks.appendChild(hiddenLinks.firstChild);
    }
    toggleBtn.classList.add('hidden');

    // Calculate available width: container width minus fixed elements
    var navWidth = nav.offsetWidth;
    var siteLogo = nav.querySelector('.site-logo');
    var siteTitle = nav.querySelector('.site-title');
    var searchInput = nav.querySelector('#search-input');
    var fixedWidth = (siteLogo ? siteLogo.getBoundingClientRect().width + 8 : 0)
                   + (siteTitle ? siteTitle.getBoundingClientRect().width + 16 : 0)
                   + (searchInput ? searchInput.getBoundingClientRect().width + 16 : 0)
                   + 32; // nav padding
    var available = navWidth - fixedWidth;
    var toggleWidth = 44;

    // Find how many items fit
    // Sum all item widths
    var totalWidth = 0;
    for (var i = 0; i < allItems.length; i++) {
      totalWidth += itemWidths[i];
    }

    var cutoff = allItems.length;

    // If everything fits, no toggle needed
    if (totalWidth > available) {
      // Need toggle — find how many items fit with toggle reserve
      totalWidth = 0;
      for (var j = 0; j < allItems.length; j++) {
        totalWidth += itemWidths[j];
        if (totalWidth > available - toggleWidth) {
          cutoff = j;
          break;
        }
      }
    }

    // Move overflow items to hidden dropdown
    for (var m = cutoff; m < allItems.length; m++) {
      hiddenLinks.appendChild(allItems[m]);
    }

    if (hiddenLinks.children.length > 0) {
      toggleBtn.classList.remove('hidden');
    } else {
      hiddenLinks.classList.add('hidden');
    }
  }

  updateNav();
  window.addEventListener('resize', updateNav);
});
