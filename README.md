

# 📺 YouTube Automation Testing Suite

This project is a Selenium-based TestNG automation suite that performs various automated validations on **YouTube.com**. The suite interacts with several tabs (About, Movies, Music, News) and performs assertions using TestNG with a focus on real-world UI validations like text matching, element visibility, data-driven search validations, and more.

---

## ✅ Test Case Overview

### 🔹 **testCase01 – Validate About Section**
- Navigate to [YouTube.com](https://www.youtube.com).
- Assert that the correct URL is loaded.
- Click on the **"About"** link from the sidebar.
- Scroll and print the mission statement/message shown.

---

### 🔹 **testCase02 – Validate Top Selling Movies**
- Navigate to the **"Movies"** tab.
- Scroll to the rightmost movie in the **“Top Selling”** section.
- Validate:
  - The movie is rated **"A", "U", "U/A", "U/A13+", or "U/A16+"**
  - Its category belongs to one of the valid genres: **"Comedy", "Animation", "Drama"**

---

### 🔹 **testCase03 – Validate Music Playlist**
- Navigate to the **"Music"** tab.
- In the first section (e.g., “India’s Biggest Hits”), scroll to the **rightmost playlist**.
- Print the **playlist name**.
- Validate that the number of tracks is **less than or equal to 50**.

---

### 🔹 **testCase04 – News Post Validation**
- Navigate to the **"News"** tab.
- In the **"Latest News Posts"** section:
  - Print the **title and body** of the first 3 posts.
  - Sum and print the **total likes** on all 3 (treat empty likes as 0).

---

### 🔹 **testCase05 – Search & Scroll by Keywords**
- Search for keywords ("Movies", "Music", "Games", "India", "UK").
- For each keyword:
  - Scroll through results.
  - Stop once the **sum of views across videos** reaches **10 Crore (100M)**.

---

## ⚠️ Challenges Faced

### 1. **Dynamic Page Content**
- YouTube content loads asynchronously via JavaScript, which often results in **StaleElementReferenceException**.
- **Solution**: Used `WebDriverWait` with `ExpectedConditions.presenceOfElementLocated` or refreshed DOM references dynamically.

### 2. **Lazy Loading & Infinite Scroll**
- Sections like Music and Search require scrolling to load new content.
- **Solution**: Implemented **JavaScript scroll-based pagination**, checked element visibility via `isDisplayed()`.

---

## 🔧 Utilities and Best Practices

- ✅ Using **Wrapper class** for all UI actions (Click, Scroll, Wait, Get Text).
- ✅ Following TestNG conventions with the `@Test` annotation.
- ✅ Using **SoftAssert** for validations where applicable.

---


