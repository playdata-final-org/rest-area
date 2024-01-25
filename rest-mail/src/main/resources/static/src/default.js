import toggleColor from './js/toggleColor.js';
import './css/app.css';
import {fn} from './js/tagging.js';
import './css/tag-basic-style.css';
import $ from 'jquery';


window.toggleColor = toggleColor;
window.$ = $;
fn($,window,window.document,undefined);
