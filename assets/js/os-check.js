function selectProperTab() {
    var Name = "windows-tab"; //set default to windows
    if (navigator.userAgent.indexOf("Win") != -1) {
        Name = "windows-tab";
    }
    if (navigator.userAgent.indexOf("Mac") != -1) {
        Name = "mac-tab";
    }
    if (navigator.userAgent.indexOf("Linux") != -1) {
        Name = "linux-tab";
    }
    const copyCodeButtons = document.getElementById(Name).click();
}

document.addEventListener('DOMContentLoaded', selectProperTab, false);