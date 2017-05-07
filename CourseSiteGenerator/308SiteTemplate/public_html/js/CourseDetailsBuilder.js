/* 
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

var sites;


function Site(use, id, navbarTitle, fileName, script){
    this.use = use;
    this.id = id;
    this.navbarTitle = navbarTitle;
    this.fileName = fileName;
    this.script = script;  
}

function initCourseDetails(){
    sites = new Array();
    var dataFile = "./js/CDData.json";
    loadCDData(dataFile);
}

function loadCDData(jsonFile) {
    $.getJSON(jsonFile, function (json) {
	loadSites(json);
        addTitle(json);
        addBanner(json);
        addImages(json);
	addNavbar();
        buildInstrLink(json);
        addStylesheet(json);
        
    });
}

function loadSites(data){
    for(var i = 0; i < data.site_templates.length; i++){
        var siteData = data.site_templates[i];
        var site = new Site(siteData.use, siteData.id, siteData.navbarTitle, siteData.fileName, siteData.script);
        sites[i] = site;
    }
}

function addNavbar(){
    var navbar = $("#navbarTitles");
    var htmlName = location.pathname.split("/").slice(-1);
    var text = "";
    for(var i = 0; i < sites.length; i++){
        if(sites[i].use === true){
            text += '<a class="';
            var fileName = sites[i].fileName;
            if(fileName === htmlName[0]){
                text += 'open_nav" href="';
            }else{
                text += 'nav" href="';
            }
            text += fileName;
            text += '" id="';
            var id = sites[i].id;
            text+= id;
            text+= '">';
            var navbarTitle = sites[i].navbarTitle;
            text+= navbarTitle;
            text+= '</a>';
        }
    }
    navbar.append(text);
}

function addTitle(data){
    var text = data.subject;
    text+= " " + data.number;
    document.title = text;
}

function addBanner(data){
    var banner = $("#banner");
    var text = data.subject;
    text+= " " + data.number + " - " + data.semester + " " + data.year + "<br>";
    text+= data.title;
    banner.append(text);
}

function addImages(data){
    var bannerImage = $("#banner_image");
    var text = '<a><img class="sbu_navbar" src="./images/' + data.bannerImagePath + '"></a>';
    bannerImage.append(text);
    var leftFoot = $("#left_foot_image");
    var leftFootText = '<a><img class="photo_floating_left" src="./images/' + data.leftFootPath + '"></a>';
    leftFoot.append(leftFootText);
    var rightFoot = $("#right_foot_image");
    var rightFootText = '<a><img class="photo_floating_right" src="./images/' + data.rightFootPath + '"></a>';
    rightFoot.append(rightFootText);
}

function buildInstrLink(data){
    var instrLink = $("#instructor_link");
    var text = '<a href="';
    text+= data.instructorHome;
    text+= '">';
    text+= data.instructorName;
    text+= '</a>';
    instrLink.append(text);
}

function addStylesheet(data){
    var stylesheet = $("#stylesheet");
    var text = '<link rel="stylesheet" type="text/css" href="./css/';
    text += data.stylesheet;
    text+= '">';
    stylesheet.append(text);
}