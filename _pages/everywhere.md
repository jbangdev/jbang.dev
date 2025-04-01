---
layout: single
title: "JBang Runs Everywhere"
permalink: /everywhere/
header:
  overlay_color: "#000"
  overlay_filter: "0.5"
  overlay_image: /images/jbang-everywhere-banner.jpg
excerpt: "Run Java apps anywhere, anyway you want"
---

<div class="slot-machine-container">
  <div class="slot-machine">
    <div class="slot-viewport launcher">
      <div class="slot-wheel">
        <div class="slot-item">jbang</div>
        <div class="slot-item">npx @jbangdev/java</div>
        <div class="slot-item">uvx jbang</div>
        <div class="slot-item">pipx jbang</div>
        <div class="slot-item">docker run jbangdev/jbang</div>
      </div>
    </div>
    <div class="slot-viewport target">
      <div class="slot-wheel">
        <div class="slot-item">app.java</div>
        <div class="slot-item">https://example.com/app.jar</div>
        <div class="slot-item">org.example:cool-app:1.0</div>
        <div class="slot-item">@catalog-alias</div>
      </div>
    </div>
  </div>
  <button id="spin-button" class="btn btn--primary">Spin!</button>
</div>

<style>
.slot-machine-container {
  max-width: 800px;
  margin: 2rem auto;
  text-align: center;
}

.slot-machine {
  display: flex;
  justify-content: center;
  gap: 2rem;
  background: #2a2a2a;
  padding: 2rem;
  border-radius: 1rem;
  box-shadow: 0 0 20px rgba(0,0,0,0.3);
}

.slot-viewport {
  background: #fff;
  padding: 1rem;
  border-radius: 0.5rem;
  height: 60px;
  position: relative;
  overflow: hidden;
  cursor: grab;
  touch-action: pan-y;
}

.slot-viewport.dragging {
  cursor: grabbing;
}

.slot-wheel {
  position: absolute;
  width: 100%;
  left: 0;
  top: 0;
  transform: translateY(0);
  transition: transform 2.5s cubic-bezier(0.21, 0.53, 0.29, 0.99);
  user-select: none;
}

.slot-wheel.no-transition {
  transition: none;
}

.launcher {
  min-width: 400px;
  width: 400px;
}

.target {
  min-width: 300px;
  width: 300px;
}

.slot-item {
  height: 60px;
  display: flex;
  align-items: center;
  justify-content: center;
  font-family: monospace;
  font-size: 1.1rem;
  color: #333;
  padding: 0 1rem;
  white-space: nowrap;
  text-align: center;
}

.slot-viewport::before,
.slot-viewport::after {
  content: '';
  position: absolute;
  left: 0;
  right: 0;
  height: 25px;
  z-index: 1;
  pointer-events: none;
}

.slot-viewport::before {
  top: 0;
  background: linear-gradient(to bottom, rgba(255,255,255,1) 0%, rgba(255,255,255,0) 100%);
}

.slot-viewport::after {
  bottom: 0;
  background: linear-gradient(to top, rgba(255,255,255,1) 0%, rgba(255,255,255,0) 100%);
}

#spin-button {
  margin-top: 2rem;
  font-size: 1.2rem;
  padding: 0.8rem 2rem;
}
</style>

<script>
document.addEventListener('DOMContentLoaded', function() {
  const wheels = document.querySelectorAll('.slot-wheel');
  const viewports = document.querySelectorAll('.slot-viewport');
  const spinButton = document.getElementById('spin-button');
  let isSpinning = false;

  function getRandomInt(min, max) {
    return Math.floor(Math.random() * (max - min + 1)) + min;
  }

  // Clone items for infinite scroll
  wheels.forEach(wheel => {
    const items = wheel.querySelectorAll('.slot-item');
    const itemsArray = Array.from(items);
    
    // Add some padding items at the top and bottom to prevent white space
    const paddingItem = document.createElement('div');
    paddingItem.className = 'slot-item';
    paddingItem.style.visibility = 'hidden';
    wheel.prepend(paddingItem.cloneNode(true));
    wheel.append(paddingItem.cloneNode(true));
    
    // Clone items multiple times to ensure smooth infinite scroll
    for (let i = 0; i < 4; i++) {
      itemsArray.forEach(item => {
        wheel.appendChild(item.cloneNode(true));
      });
    }
  });

  // Calculate vertical offset to center item in viewport
  function calculateCenterOffset(wheel, index) {
    const viewport = wheel.closest('.slot-viewport');
    const itemHeight = wheel.querySelector('.slot-item').offsetHeight;
    const viewportHeight = viewport.offsetHeight;
    const offset = (viewportHeight - itemHeight) / 2;
    return -index * itemHeight + offset;
  }

  // Center the items in the viewport
  function centerItems() {
    wheels.forEach(wheel => {
      const visibleIndex = Math.floor(Math.random() * 4) + 1; // +1 to account for padding item
      const offset = calculateCenterOffset(wheel, visibleIndex);
      wheel.style.transform = `translateY(${offset}px)`;
    });
  }

  // Call centerItems after a short delay to ensure layout is complete
  setTimeout(centerItems, 100);

  // Add drag functionality
  viewports.forEach((viewport, index) => {
    const wheel = wheels[index];
    let isDragging = false;
    let startY = 0;
    let wheelStartY = 0;
    const items = wheel.querySelectorAll('.slot-item');
    const originalSetCount = (items.length - 2) / 5; // Original item count (minus padding items)

    function getTranslateY(element) {
      const style = window.getComputedStyle(element);
      const matrix = new WebKitCSSMatrix(style.transform);
      return matrix.m42;
    }

    function getBounds() {
      const itemHeight = wheel.querySelector('.slot-item').offsetHeight;
      const viewportHeight = viewport.offsetHeight;
      const offset = (viewportHeight - itemHeight) / 2;
      const totalOriginalHeight = itemHeight * originalSetCount;
      
      // Allow scrolling with appropriate bounds
      return {
        min: -totalOriginalHeight + offset,
        max: offset + itemHeight,
        itemHeight: itemHeight,
        offset: offset
      };
    }

    function onDragStart(e) {
      if (isSpinning) return;
      isDragging = true;
      viewport.classList.add('dragging');
      wheel.classList.add('no-transition');
      startY = e.type === 'mousedown' ? e.clientY : e.touches[0].clientY;
      wheelStartY = getTranslateY(wheel);
    }

    function onDragMove(e) {
      if (!isDragging) return;
      e.preventDefault();
      const currentY = e.type === 'mousemove' ? e.clientY : e.touches[0].clientY;
      const deltaY = currentY - startY;
      
      // Apply resistance as we approach boundaries
      const bounds = getBounds();
      let newPosition = wheelStartY + deltaY;
      
      // Add resistance at boundaries
      if (newPosition > bounds.max) {
        const overscroll = newPosition - bounds.max;
        newPosition = bounds.max + overscroll * 0.3; // Resistance factor
      } else if (newPosition < bounds.min) {
        const overscroll = bounds.min - newPosition;
        newPosition = bounds.min - overscroll * 0.3; // Resistance factor
      }
      
      wheel.style.transform = `translateY(${newPosition}px)`;
    }

    function onDragEnd() {
      if (!isDragging) return;
      isDragging = false;
      viewport.classList.remove('dragging');
      wheel.classList.remove('no-transition');

      // Get bounds
      const bounds = getBounds();
      const currentY = getTranslateY(wheel);
      
      // If we're outside bounds, animate back
      if (currentY > bounds.max) {
        wheel.style.transform = `translateY(${bounds.max}px)`;
      } else if (currentY < bounds.min) {
        wheel.style.transform = `translateY(${bounds.min}px)`;
      } else {
        // Snap to nearest item, adjusted for center offset
        const snapIndex = Math.round((currentY - bounds.offset) / bounds.itemHeight);
        const snapY = snapIndex * bounds.itemHeight + bounds.offset;
        wheel.style.transform = `translateY(${snapY}px)`;
      }
    }

    // Mouse events
    viewport.addEventListener('mousedown', onDragStart);
    window.addEventListener('mousemove', onDragMove);
    window.addEventListener('mouseup', onDragEnd);

    // Touch events
    viewport.addEventListener('touchstart', onDragStart);
    window.addEventListener('touchmove', onDragMove, { passive: false });
    window.addEventListener('touchend', onDragEnd);
  });

  function spin() {
    if (isSpinning) return;
    isSpinning = true;
    
    wheels.forEach(wheel => {
      wheel.classList.remove('no-transition');
      
      // Get all items and identify the original items (not clones or padding)
      const allItems = wheel.querySelectorAll('.slot-item');
      const originals = [];
      
      // Find the original visible items
      for (let i = 1; i < allItems.length; i++) {
        // Skip hidden items
        if (allItems[i].style && allItems[i].style.visibility === 'hidden') continue;
        
        // Collect only the first set of items
        if (originals.length < 5) {
          originals.push(allItems[i]);
        } else {
          break;
        }
      }
      
      const itemHeight = originals[0].offsetHeight;
      const viewport = wheel.closest('.slot-viewport');
      const viewportHeight = viewport.offsetHeight;
      const offset = (viewportHeight - itemHeight) / 2;
      
      // Pick a random index from the original set of items
      const finalIndex = getRandomInt(0, originals.length - 1);
      
      // Calculate final position - this is where we want to land
      const finalPosition = -(finalIndex + 1) * itemHeight + offset;
      
      // First determine where we are now
      const currentPos = getTranslateY(wheel);
      
      // Calculate rotations - full rotations plus distance to final item
      // Each original set is 5 items
      const spins = 2 + getRandomInt(0, 1); // 2-3 full rotations
      const spinDistance = spins * originals.length * itemHeight;
      
      // Calculate exact target position so the animation ends at final position
      // We need to get from current position to final position plus full spins
      // This ensures we end exactly where we want
      const targetPosition = currentPos - spinDistance - 
                            (Math.abs(currentPos - finalPosition) % (originals.length * itemHeight));
      
      // Animate to target
      wheel.style.transition = 'transform 2.5s cubic-bezier(0.21, 0.53, 0.29, 0.99)';
      wheel.style.transform = `translateY(${targetPosition}px)`;
      
      // No need to reset, we calculated to end at final position
      wheel.addEventListener('transitionend', function() {
        isSpinning = false;
      }, { once: true });
    });
  }
  
  // Helper function to get translateY value
  function getTranslateY(element) {
    const style = window.getComputedStyle(element);
    const matrix = new WebKitCSSMatrix(style.transform);
    return matrix.m42;
  }

  spinButton.addEventListener('click', spin);
});
</script> 