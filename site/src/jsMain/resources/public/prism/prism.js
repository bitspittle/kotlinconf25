/* PrismJS 1.29.0
https://prismjs.com/download.html#themes=prism&languages=markup+css+clike+javascript+bash+diff+docker+kotlin+markdown+toml+typescript+yaml&plugins=line-highlight+diff-highlight */
var _self = "undefined" != typeof window ? window : "undefined" != typeof WorkerGlobalScope && self instanceof WorkerGlobalScope ? self : {},
    Prism = function (e) {
        var n = /(?:^|\s)lang(?:uage)?-([\w-]+)(?=\s|$)/i, t = 0, r = {}, a = {
            manual: e.Prism && e.Prism.manual,
            disableWorkerMessageHandler: e.Prism && e.Prism.disableWorkerMessageHandler,
            util: {
                encode: function e(n) {
                    return n instanceof i ? new i(n.type, e(n.content), n.alias) : Array.isArray(n) ? n.map(e) : n.replace(/&/g, "&amp;").replace(/</g, "&lt;").replace(/\u00a0/g, " ")
                }, type: function (e) {
                    return Object.prototype.toString.call(e).slice(8, -1)
                }, objId: function (e) {
                    return e.__id || Object.defineProperty(e, "__id", {value: ++t}), e.__id
                }, clone: function e(n, t) {
                    var r, i;
                    switch (t = t || {}, a.util.type(n)) {
                        case"Object":
                            if (i = a.util.objId(n), t[i]) return t[i];
                            for (var l in r = {}, t[i] = r, n) n.hasOwnProperty(l) && (r[l] = e(n[l], t));
                            return r;
                        case"Array":
                            return i = a.util.objId(n), t[i] ? t[i] : (r = [], t[i] = r, n.forEach((function (n, a) {
                                r[a] = e(n, t)
                            })), r);
                        default:
                            return n
                    }
                }, getLanguage: function (e) {
                    for (; e;) {
                        var t = n.exec(e.className);
                        if (t) return t[1].toLowerCase();
                        e = e.parentElement
                    }
                    return "none"
                }, setLanguage: function (e, t) {
                    e.className = e.className.replace(RegExp(n, "gi"), ""), e.classList.add("language-" + t)
                }, currentScript: function () {
                    if ("undefined" == typeof document) return null;
                    if ("currentScript" in document) return document.currentScript;
                    try {
                        throw new Error
                    } catch (r) {
                        var e = (/at [^(\r\n]*\((.*):[^:]+:[^:]+\)$/i.exec(r.stack) || [])[1];
                        if (e) {
                            var n = document.getElementsByTagName("script");
                            for (var t in n) if (n[t].src == e) return n[t]
                        }
                        return null
                    }
                }, isActive: function (e, n, t) {
                    for (var r = "no-" + n; e;) {
                        var a = e.classList;
                        if (a.contains(n)) return !0;
                        if (a.contains(r)) return !1;
                        e = e.parentElement
                    }
                    return !!t
                }
            },
            languages: {
                plain: r, plaintext: r, text: r, txt: r, extend: function (e, n) {
                    var t = a.util.clone(a.languages[e]);
                    for (var r in n) t[r] = n[r];
                    return t
                }, insertBefore: function (e, n, t, r) {
                    var i = (r = r || a.languages)[e], l = {};
                    for (var o in i) if (i.hasOwnProperty(o)) {
                        if (o == n) for (var s in t) t.hasOwnProperty(s) && (l[s] = t[s]);
                        t.hasOwnProperty(o) || (l[o] = i[o])
                    }
                    var u = r[e];
                    return r[e] = l, a.languages.DFS(a.languages, (function (n, t) {
                        t === u && n != e && (this[n] = l)
                    })), l
                }, DFS: function e(n, t, r, i) {
                    i = i || {};
                    var l = a.util.objId;
                    for (var o in n) if (n.hasOwnProperty(o)) {
                        t.call(n, o, n[o], r || o);
                        var s = n[o], u = a.util.type(s);
                        "Object" !== u || i[l(s)] ? "Array" !== u || i[l(s)] || (i[l(s)] = !0, e(s, t, o, i)) : (i[l(s)] = !0, e(s, t, null, i))
                    }
                }
            },
            plugins: {},
            highlightAll: function (e, n) {
                a.highlightAllUnder(document, e, n)
            },
            highlightAllUnder: function (e, n, t) {
                var r = {
                    callback: t,
                    container: e,
                    selector: 'code[class*="language-"], [class*="language-"] code, code[class*="lang-"], [class*="lang-"] code'
                };
                a.hooks.run("before-highlightall", r), r.elements = Array.prototype.slice.apply(r.container.querySelectorAll(r.selector)), a.hooks.run("before-all-elements-highlight", r);
                for (var i, l = 0; i = r.elements[l++];) a.highlightElement(i, !0 === n, r.callback)
            },
            highlightElement: function (n, t, r) {
                var i = a.util.getLanguage(n), l = a.languages[i];
                a.util.setLanguage(n, i);
                var o = n.parentElement;
                o && "pre" === o.nodeName.toLowerCase() && a.util.setLanguage(o, i);
                var s = {element: n, language: i, grammar: l, code: n.textContent};

                function u(e) {
                    s.highlightedCode = e, a.hooks.run("before-insert", s), s.element.innerHTML = s.highlightedCode, a.hooks.run("after-highlight", s), a.hooks.run("complete", s), r && r.call(s.element)
                }

                if (a.hooks.run("before-sanity-check", s), (o = s.element.parentElement) && "pre" === o.nodeName.toLowerCase() && !o.hasAttribute("tabindex") && o.setAttribute("tabindex", "0"), !s.code) return a.hooks.run("complete", s), void (r && r.call(s.element));
                if (a.hooks.run("before-highlight", s), s.grammar) if (t && e.Worker) {
                    var c = new Worker(a.filename);
                    c.onmessage = function (e) {
                        u(e.data)
                    }, c.postMessage(JSON.stringify({language: s.language, code: s.code, immediateClose: !0}))
                } else u(a.highlight(s.code, s.grammar, s.language)); else u(a.util.encode(s.code))
            },
            highlight: function (e, n, t) {
                var r = {code: e, grammar: n, language: t};
                if (a.hooks.run("before-tokenize", r), !r.grammar) throw new Error('The language "' + r.language + '" has no grammar.');
                return r.tokens = a.tokenize(r.code, r.grammar), a.hooks.run("after-tokenize", r), i.stringify(a.util.encode(r.tokens), r.language)
            },
            tokenize: function (e, n) {
                var t = n.rest;
                if (t) {
                    for (var r in t) n[r] = t[r];
                    delete n.rest
                }
                var a = new s;
                return u(a, a.head, e), o(e, a, n, a.head, 0), function (e) {
                    for (var n = [], t = e.head.next; t !== e.tail;) n.push(t.value), t = t.next;
                    return n
                }(a)
            },
            hooks: {
                all: {}, add: function (e, n) {
                    var t = a.hooks.all;
                    t[e] = t[e] || [], t[e].push(n)
                }, run: function (e, n) {
                    var t = a.hooks.all[e];
                    if (t && t.length) for (var r, i = 0; r = t[i++];) r(n)
                }
            },
            Token: i
        };

        function i(e, n, t, r) {
            this.type = e, this.content = n, this.alias = t, this.length = 0 | (r || "").length
        }

        function l(e, n, t, r) {
            e.lastIndex = n;
            var a = e.exec(t);
            if (a && r && a[1]) {
                var i = a[1].length;
                a.index += i, a[0] = a[0].slice(i)
            }
            return a
        }

        function o(e, n, t, r, s, g) {
            for (var f in t) if (t.hasOwnProperty(f) && t[f]) {
                var h = t[f];
                h = Array.isArray(h) ? h : [h];
                for (var d = 0; d < h.length; ++d) {
                    if (g && g.cause == f + "," + d) return;
                    var v = h[d], p = v.inside, m = !!v.lookbehind, y = !!v.greedy, k = v.alias;
                    if (y && !v.pattern.global) {
                        var x = v.pattern.toString().match(/[imsuy]*$/)[0];
                        v.pattern = RegExp(v.pattern.source, x + "g")
                    }
                    for (var b = v.pattern || v, w = r.next, A = s; w !== n.tail && !(g && A >= g.reach); A += w.value.length, w = w.next) {
                        var E = w.value;
                        if (n.length > e.length) return;
                        if (!(E instanceof i)) {
                            var P, L = 1;
                            if (y) {
                                if (!(P = l(b, A, e, m)) || P.index >= e.length) break;
                                var S = P.index, O = P.index + P[0].length, j = A;
                                for (j += w.value.length; S >= j;) j += (w = w.next).value.length;
                                if (A = j -= w.value.length, w.value instanceof i) continue;
                                for (var C = w; C !== n.tail && (j < O || "string" == typeof C.value); C = C.next) L++, j += C.value.length;
                                L--, E = e.slice(A, j), P.index -= A
                            } else if (!(P = l(b, 0, E, m))) continue;
                            S = P.index;
                            var N = P[0], _ = E.slice(0, S), M = E.slice(S + N.length), W = A + E.length;
                            g && W > g.reach && (g.reach = W);
                            var z = w.prev;
                            if (_ && (z = u(n, z, _), A += _.length), c(n, z, L), w = u(n, z, new i(f, p ? a.tokenize(N, p) : N, k, N)), M && u(n, w, M), L > 1) {
                                var I = {cause: f + "," + d, reach: W};
                                o(e, n, t, w.prev, A, I), g && I.reach > g.reach && (g.reach = I.reach)
                            }
                        }
                    }
                }
            }
        }

        function s() {
            var e = {value: null, prev: null, next: null}, n = {value: null, prev: e, next: null};
            e.next = n, this.head = e, this.tail = n, this.length = 0
        }

        function u(e, n, t) {
            var r = n.next, a = {value: t, prev: n, next: r};
            return n.next = a, r.prev = a, e.length++, a
        }

        function c(e, n, t) {
            for (var r = n.next, a = 0; a < t && r !== e.tail; a++) r = r.next;
            n.next = r, r.prev = n, e.length -= a
        }

        if (e.Prism = a, i.stringify = function e(n, t) {
            if ("string" == typeof n) return n;
            if (Array.isArray(n)) {
                var r = "";
                return n.forEach((function (n) {
                    r += e(n, t)
                })), r
            }
            var i = {
                type: n.type,
                content: e(n.content, t),
                tag: "span",
                classes: ["token", n.type],
                attributes: {},
                language: t
            }, l = n.alias;
            l && (Array.isArray(l) ? Array.prototype.push.apply(i.classes, l) : i.classes.push(l)), a.hooks.run("wrap", i);
            var o = "";
            for (var s in i.attributes) o += " " + s + '="' + (i.attributes[s] || "").replace(/"/g, "&quot;") + '"';
            return "<" + i.tag + ' class="' + i.classes.join(" ") + '"' + o + ">" + i.content + "</" + i.tag + ">"
        }, !e.document) return e.addEventListener ? (a.disableWorkerMessageHandler || e.addEventListener("message", (function (n) {
            var t = JSON.parse(n.data), r = t.language, i = t.code, l = t.immediateClose;
            e.postMessage(a.highlight(i, a.languages[r], r)), l && e.close()
        }), !1), a) : a;
        var g = a.util.currentScript();

        function f() {
            a.manual || a.highlightAll()
        }

        if (g && (a.filename = g.src, g.hasAttribute("data-manual") && (a.manual = !0)), !a.manual) {
            var h = document.readyState;
            "loading" === h || "interactive" === h && g && g.defer ? document.addEventListener("DOMContentLoaded", f) : window.requestAnimationFrame ? window.requestAnimationFrame(f) : window.setTimeout(f, 16)
        }
        return a
    }(_self);
"undefined" != typeof module && module.exports && (module.exports = Prism), "undefined" != typeof global && (global.Prism = Prism);
Prism.languages.markup = {
    comment: {pattern: /<!--(?:(?!<!--)[\s\S])*?-->/, greedy: !0},
    prolog: {pattern: /<\?[\s\S]+?\?>/, greedy: !0},
    doctype: {
        pattern: /<!DOCTYPE(?:[^>"'[\]]|"[^"]*"|'[^']*')+(?:\[(?:[^<"'\]]|"[^"]*"|'[^']*'|<(?!!--)|<!--(?:[^-]|-(?!->))*-->)*\]\s*)?>/i,
        greedy: !0,
        inside: {
            "internal-subset": {pattern: /(^[^\[]*\[)[\s\S]+(?=\]>$)/, lookbehind: !0, greedy: !0, inside: null},
            string: {pattern: /"[^"]*"|'[^']*'/, greedy: !0},
            punctuation: /^<!|>$|[[\]]/,
            "doctype-tag": /^DOCTYPE/i,
            name: /[^\s<>'"]+/
        }
    },
    cdata: {pattern: /<!\[CDATA\[[\s\S]*?\]\]>/i, greedy: !0},
    tag: {
        pattern: /<\/?(?!\d)[^\s>\/=$<%]+(?:\s(?:\s*[^\s>\/=]+(?:\s*=\s*(?:"[^"]*"|'[^']*'|[^\s'">=]+(?=[\s>]))|(?=[\s/>])))+)?\s*\/?>/,
        greedy: !0,
        inside: {
            tag: {pattern: /^<\/?[^\s>\/]+/, inside: {punctuation: /^<\/?/, namespace: /^[^\s>\/:]+:/}},
            "special-attr": [],
            "attr-value": {
                pattern: /=\s*(?:"[^"]*"|'[^']*'|[^\s'">=]+)/,
                inside: {
                    punctuation: [{pattern: /^=/, alias: "attr-equals"}, {
                        pattern: /^(\s*)["']|["']$/,
                        lookbehind: !0
                    }]
                }
            },
            punctuation: /\/?>/,
            "attr-name": {pattern: /[^\s>\/]+/, inside: {namespace: /^[^\s>\/:]+:/}}
        }
    },
    entity: [{pattern: /&[\da-z]{1,8};/i, alias: "named-entity"}, /&#x?[\da-f]{1,8};/i]
}, Prism.languages.markup.tag.inside["attr-value"].inside.entity = Prism.languages.markup.entity, Prism.languages.markup.doctype.inside["internal-subset"].inside = Prism.languages.markup, Prism.hooks.add("wrap", (function (a) {
    "entity" === a.type && (a.attributes.title = a.content.replace(/&amp;/, "&"))
})), Object.defineProperty(Prism.languages.markup.tag, "addInlined", {
    value: function (a, e) {
        var s = {};
        s["language-" + e] = {
            pattern: /(^<!\[CDATA\[)[\s\S]+?(?=\]\]>$)/i,
            lookbehind: !0,
            inside: Prism.languages[e]
        }, s.cdata = /^<!\[CDATA\[|\]\]>$/i;
        var t = {"included-cdata": {pattern: /<!\[CDATA\[[\s\S]*?\]\]>/i, inside: s}};
        t["language-" + e] = {pattern: /[\s\S]+/, inside: Prism.languages[e]};
        var n = {};
        n[a] = {
            pattern: RegExp("(<__[^>]*>)(?:<!\\[CDATA\\[(?:[^\\]]|\\](?!\\]>))*\\]\\]>|(?!<!\\[CDATA\\[)[^])*?(?=</__>)".replace(/__/g, (function () {
                return a
            })), "i"), lookbehind: !0, greedy: !0, inside: t
        }, Prism.languages.insertBefore("markup", "cdata", n)
    }
}), Object.defineProperty(Prism.languages.markup.tag, "addAttribute", {
    value: function (a, e) {
        Prism.languages.markup.tag.inside["special-attr"].push({
            pattern: RegExp("(^|[\"'\\s])(?:" + a + ")\\s*=\\s*(?:\"[^\"]*\"|'[^']*'|[^\\s'\">=]+(?=[\\s>]))", "i"),
            lookbehind: !0,
            inside: {
                "attr-name": /^[^\s=]+/,
                "attr-value": {
                    pattern: /=[\s\S]+/,
                    inside: {
                        value: {
                            pattern: /(^=\s*(["']|(?!["'])))\S[\s\S]*(?=\2$)/,
                            lookbehind: !0,
                            alias: [e, "language-" + e],
                            inside: Prism.languages[e]
                        }, punctuation: [{pattern: /^=/, alias: "attr-equals"}, /"|'/]
                    }
                }
            }
        })
    }
}), Prism.languages.html = Prism.languages.markup, Prism.languages.mathml = Prism.languages.markup, Prism.languages.svg = Prism.languages.markup, Prism.languages.xml = Prism.languages.extend("markup", {}), Prism.languages.ssml = Prism.languages.xml, Prism.languages.atom = Prism.languages.xml, Prism.languages.rss = Prism.languages.xml;
!function (s) {
    var e = /(?:"(?:\\(?:\r\n|[\s\S])|[^"\\\r\n])*"|'(?:\\(?:\r\n|[\s\S])|[^'\\\r\n])*')/;
    s.languages.css = {
        comment: /\/\*[\s\S]*?\*\//,
        atrule: {
            pattern: RegExp("@[\\w-](?:[^;{\\s\"']|\\s+(?!\\s)|" + e.source + ")*?(?:;|(?=\\s*\\{))"),
            inside: {
                rule: /^@[\w-]+/,
                "selector-function-argument": {
                    pattern: /(\bselector\s*\(\s*(?![\s)]))(?:[^()\s]|\s+(?![\s)])|\((?:[^()]|\([^()]*\))*\))+(?=\s*\))/,
                    lookbehind: !0,
                    alias: "selector"
                },
                keyword: {pattern: /(^|[^\w-])(?:and|not|only|or)(?![\w-])/, lookbehind: !0}
            }
        },
        url: {
            pattern: RegExp("\\burl\\((?:" + e.source + "|(?:[^\\\\\r\n()\"']|\\\\[^])*)\\)", "i"),
            greedy: !0,
            inside: {
                function: /^url/i,
                punctuation: /^\(|\)$/,
                string: {pattern: RegExp("^" + e.source + "$"), alias: "url"}
            }
        },
        selector: {
            pattern: RegExp("(^|[{}\\s])[^{}\\s](?:[^{};\"'\\s]|\\s+(?![\\s{])|" + e.source + ")*(?=\\s*\\{)"),
            lookbehind: !0
        },
        string: {pattern: e, greedy: !0},
        property: {
            pattern: /(^|[^-\w\xA0-\uFFFF])(?!\s)[-_a-z\xA0-\uFFFF](?:(?!\s)[-\w\xA0-\uFFFF])*(?=\s*:)/i,
            lookbehind: !0
        },
        important: /!important\b/i,
        function: {pattern: /(^|[^-a-z0-9])[-a-z0-9]+(?=\()/i, lookbehind: !0},
        punctuation: /[(){};:,]/
    }, s.languages.css.atrule.inside.rest = s.languages.css;
    var t = s.languages.markup;
    t && (t.tag.addInlined("style", "css"), t.tag.addAttribute("style", "css"))
}(Prism);
Prism.languages.clike = {
    comment: [{
        pattern: /(^|[^\\])\/\*[\s\S]*?(?:\*\/|$)/,
        lookbehind: !0,
        greedy: !0
    }, {pattern: /(^|[^\\:])\/\/.*/, lookbehind: !0, greedy: !0}],
    string: {pattern: /(["'])(?:\\(?:\r\n|[\s\S])|(?!\1)[^\\\r\n])*\1/, greedy: !0},
    "class-name": {
        pattern: /(\b(?:class|extends|implements|instanceof|interface|new|trait)\s+|\bcatch\s+\()[\w.\\]+/i,
        lookbehind: !0,
        inside: {punctuation: /[.\\]/}
    },
    keyword: /\b(?:break|catch|continue|do|else|finally|for|function|if|in|instanceof|new|null|return|throw|try|while)\b/,
    boolean: /\b(?:false|true)\b/,
    function: /\b\w+(?=\()/,
    number: /\b0x[\da-f]+\b|(?:\b\d+(?:\.\d*)?|\B\.\d+)(?:e[+-]?\d+)?/i,
    operator: /[<>]=?|[!=]=?=?|--?|\+\+?|&&?|\|\|?|[?*/~^%]/,
    punctuation: /[{}[\];(),.:]/
};
Prism.languages.javascript = Prism.languages.extend("clike", {
    "class-name": [Prism.languages.clike["class-name"], {
        pattern: /(^|[^$\w\xA0-\uFFFF])(?!\s)[_$A-Z\xA0-\uFFFF](?:(?!\s)[$\w\xA0-\uFFFF])*(?=\.(?:constructor|prototype))/,
        lookbehind: !0
    }],
    keyword: [{
        pattern: /((?:^|\})\s*)catch\b/,
        lookbehind: !0
    }, {
        pattern: /(^|[^.]|\.\.\.\s*)\b(?:as|assert(?=\s*\{)|async(?=\s*(?:function\b|\(|[$\w\xA0-\uFFFF]|$))|await|break|case|class|const|continue|debugger|default|delete|do|else|enum|export|extends|finally(?=\s*(?:\{|$))|for|from(?=\s*(?:['"]|$))|function|(?:get|set)(?=\s*(?:[#\[$\w\xA0-\uFFFF]|$))|if|implements|import|in|instanceof|interface|let|new|null|of|package|private|protected|public|return|static|super|switch|this|throw|try|typeof|undefined|var|void|while|with|yield)\b/,
        lookbehind: !0
    }],
    function: /#?(?!\s)[_$a-zA-Z\xA0-\uFFFF](?:(?!\s)[$\w\xA0-\uFFFF])*(?=\s*(?:\.\s*(?:apply|bind|call)\s*)?\()/,
    number: {
        pattern: RegExp("(^|[^\\w$])(?:NaN|Infinity|0[bB][01]+(?:_[01]+)*n?|0[oO][0-7]+(?:_[0-7]+)*n?|0[xX][\\dA-Fa-f]+(?:_[\\dA-Fa-f]+)*n?|\\d+(?:_\\d+)*n|(?:\\d+(?:_\\d+)*(?:\\.(?:\\d+(?:_\\d+)*)?)?|\\.\\d+(?:_\\d+)*)(?:[Ee][+-]?\\d+(?:_\\d+)*)?)(?![\\w$])"),
        lookbehind: !0
    },
    operator: /--|\+\+|\*\*=?|=>|&&=?|\|\|=?|[!=]==|<<=?|>>>?=?|[-+*/%&|^!=<>]=?|\.{3}|\?\?=?|\?\.?|[~:]/
}), Prism.languages.javascript["class-name"][0].pattern = /(\b(?:class|extends|implements|instanceof|interface|new)\s+)[\w.\\]+/, Prism.languages.insertBefore("javascript", "keyword", {
    regex: {
        pattern: RegExp("((?:^|[^$\\w\\xA0-\\uFFFF.\"'\\])\\s]|\\b(?:return|yield))\\s*)/(?:(?:\\[(?:[^\\]\\\\\r\n]|\\\\.)*\\]|\\\\.|[^/\\\\\\[\r\n])+/[dgimyus]{0,7}|(?:\\[(?:[^[\\]\\\\\r\n]|\\\\.|\\[(?:[^[\\]\\\\\r\n]|\\\\.|\\[(?:[^[\\]\\\\\r\n]|\\\\.)*\\])*\\])*\\]|\\\\.|[^/\\\\\\[\r\n])+/[dgimyus]{0,7}v[dgimyus]{0,7})(?=(?:\\s|/\\*(?:[^*]|\\*(?!/))*\\*/)*(?:$|[\r\n,.;:})\\]]|//))"),
        lookbehind: !0,
        greedy: !0,
        inside: {
            "regex-source": {
                pattern: /^(\/)[\s\S]+(?=\/[a-z]*$)/,
                lookbehind: !0,
                alias: "language-regex",
                inside: Prism.languages.regex
            }, "regex-delimiter": /^\/|\/$/, "regex-flags": /^[a-z]+$/
        }
    },
    "function-variable": {
        pattern: /#?(?!\s)[_$a-zA-Z\xA0-\uFFFF](?:(?!\s)[$\w\xA0-\uFFFF])*(?=\s*[=:]\s*(?:async\s*)?(?:\bfunction\b|(?:\((?:[^()]|\([^()]*\))*\)|(?!\s)[_$a-zA-Z\xA0-\uFFFF](?:(?!\s)[$\w\xA0-\uFFFF])*)\s*=>))/,
        alias: "function"
    },
    parameter: [{
        pattern: /(function(?:\s+(?!\s)[_$a-zA-Z\xA0-\uFFFF](?:(?!\s)[$\w\xA0-\uFFFF])*)?\s*\(\s*)(?!\s)(?:[^()\s]|\s+(?![\s)])|\([^()]*\))+(?=\s*\))/,
        lookbehind: !0,
        inside: Prism.languages.javascript
    }, {
        pattern: /(^|[^$\w\xA0-\uFFFF])(?!\s)[_$a-z\xA0-\uFFFF](?:(?!\s)[$\w\xA0-\uFFFF])*(?=\s*=>)/i,
        lookbehind: !0,
        inside: Prism.languages.javascript
    }, {
        pattern: /(\(\s*)(?!\s)(?:[^()\s]|\s+(?![\s)])|\([^()]*\))+(?=\s*\)\s*=>)/,
        lookbehind: !0,
        inside: Prism.languages.javascript
    }, {
        pattern: /((?:\b|\s|^)(?!(?:as|async|await|break|case|catch|class|const|continue|debugger|default|delete|do|else|enum|export|extends|finally|for|from|function|get|if|implements|import|in|instanceof|interface|let|new|null|of|package|private|protected|public|return|set|static|super|switch|this|throw|try|typeof|undefined|var|void|while|with|yield)(?![$\w\xA0-\uFFFF]))(?:(?!\s)[_$a-zA-Z\xA0-\uFFFF](?:(?!\s)[$\w\xA0-\uFFFF])*\s*)\(\s*|\]\s*\(\s*)(?!\s)(?:[^()\s]|\s+(?![\s)])|\([^()]*\))+(?=\s*\)\s*\{)/,
        lookbehind: !0,
        inside: Prism.languages.javascript
    }],
    constant: /\b[A-Z](?:[A-Z_]|\dx?)*\b/
}), Prism.languages.insertBefore("javascript", "string", {
    hashbang: {pattern: /^#!.*/, greedy: !0, alias: "comment"},
    "template-string": {
        pattern: /`(?:\\[\s\S]|\$\{(?:[^{}]|\{(?:[^{}]|\{[^}]*\})*\})+\}|(?!\$\{)[^\\`])*`/,
        greedy: !0,
        inside: {
            "template-punctuation": {pattern: /^`|`$/, alias: "string"},
            interpolation: {
                pattern: /((?:^|[^\\])(?:\\{2})*)\$\{(?:[^{}]|\{(?:[^{}]|\{[^}]*\})*\})+\}/,
                lookbehind: !0,
                inside: {
                    "interpolation-punctuation": {pattern: /^\$\{|\}$/, alias: "punctuation"},
                    rest: Prism.languages.javascript
                }
            },
            string: /[\s\S]+/
        }
    },
    "string-property": {
        pattern: /((?:^|[,{])[ \t]*)(["'])(?:\\(?:\r\n|[\s\S])|(?!\2)[^\\\r\n])*\2(?=\s*:)/m,
        lookbehind: !0,
        greedy: !0,
        alias: "property"
    }
}), Prism.languages.insertBefore("javascript", "operator", {
    "literal-property": {
        pattern: /((?:^|[,{])[ \t]*)(?!\s)[_$a-zA-Z\xA0-\uFFFF](?:(?!\s)[$\w\xA0-\uFFFF])*(?=\s*:)/m,
        lookbehind: !0,
        alias: "property"
    }
}), Prism.languages.markup && (Prism.languages.markup.tag.addInlined("script", "javascript"), Prism.languages.markup.tag.addAttribute("on(?:abort|blur|change|click|composition(?:end|start|update)|dblclick|error|focus(?:in|out)?|key(?:down|up)|load|mouse(?:down|enter|leave|move|out|over|up)|reset|resize|scroll|select|slotchange|submit|unload|wheel)", "javascript")), Prism.languages.js = Prism.languages.javascript;
!function (e) {
    var t = "\\b(?:BASH|BASHOPTS|BASH_ALIASES|BASH_ARGC|BASH_ARGV|BASH_CMDS|BASH_COMPLETION_COMPAT_DIR|BASH_LINENO|BASH_REMATCH|BASH_SOURCE|BASH_VERSINFO|BASH_VERSION|COLORTERM|COLUMNS|COMP_WORDBREAKS|DBUS_SESSION_BUS_ADDRESS|DEFAULTS_PATH|DESKTOP_SESSION|DIRSTACK|DISPLAY|EUID|GDMSESSION|GDM_LANG|GNOME_KEYRING_CONTROL|GNOME_KEYRING_PID|GPG_AGENT_INFO|GROUPS|HISTCONTROL|HISTFILE|HISTFILESIZE|HISTSIZE|HOME|HOSTNAME|HOSTTYPE|IFS|INSTANCE|JOB|LANG|LANGUAGE|LC_ADDRESS|LC_ALL|LC_IDENTIFICATION|LC_MEASUREMENT|LC_MONETARY|LC_NAME|LC_NUMERIC|LC_PAPER|LC_TELEPHONE|LC_TIME|LESSCLOSE|LESSOPEN|LINES|LOGNAME|LS_COLORS|MACHTYPE|MAILCHECK|MANDATORY_PATH|NO_AT_BRIDGE|OLDPWD|OPTERR|OPTIND|ORBIT_SOCKETDIR|OSTYPE|PAPERSIZE|PATH|PIPESTATUS|PPID|PS1|PS2|PS3|PS4|PWD|RANDOM|REPLY|SECONDS|SELINUX_INIT|SESSION|SESSIONTYPE|SESSION_MANAGER|SHELL|SHELLOPTS|SHLVL|SSH_AUTH_SOCK|TERM|UID|UPSTART_EVENTS|UPSTART_INSTANCE|UPSTART_JOB|UPSTART_SESSION|USER|WINDOWID|XAUTHORITY|XDG_CONFIG_DIRS|XDG_CURRENT_DESKTOP|XDG_DATA_DIRS|XDG_GREETER_DATA_DIR|XDG_MENU_PREFIX|XDG_RUNTIME_DIR|XDG_SEAT|XDG_SEAT_PATH|XDG_SESSION_DESKTOP|XDG_SESSION_ID|XDG_SESSION_PATH|XDG_SESSION_TYPE|XDG_VTNR|XMODIFIERS)\\b",
        a = {pattern: /(^(["']?)\w+\2)[ \t]+\S.*/, lookbehind: !0, alias: "punctuation", inside: null}, n = {
            bash: a,
            environment: {pattern: RegExp("\\$" + t), alias: "constant"},
            variable: [{
                pattern: /\$?\(\([\s\S]+?\)\)/,
                greedy: !0,
                inside: {
                    variable: [{pattern: /(^\$\(\([\s\S]+)\)\)/, lookbehind: !0}, /^\$\(\(/],
                    number: /\b0x[\dA-Fa-f]+\b|(?:\b\d+(?:\.\d*)?|\B\.\d+)(?:[Ee]-?\d+)?/,
                    operator: /--|\+\+|\*\*=?|<<=?|>>=?|&&|\|\||[=!+\-*/%<>^&|]=?|[?~:]/,
                    punctuation: /\(\(?|\)\)?|,|;/
                }
            }, {
                pattern: /\$\((?:\([^)]+\)|[^()])+\)|`[^`]+`/,
                greedy: !0,
                inside: {variable: /^\$\(|^`|\)$|`$/}
            }, {
                pattern: /\$\{[^}]+\}/,
                greedy: !0,
                inside: {
                    operator: /:[-=?+]?|[!\/]|##?|%%?|\^\^?|,,?/,
                    punctuation: /[\[\]]/,
                    environment: {pattern: RegExp("(\\{)" + t), lookbehind: !0, alias: "constant"}
                }
            }, /\$(?:\w+|[#?*!@$])/],
            entity: /\\(?:[abceEfnrtv\\"]|O?[0-7]{1,3}|U[0-9a-fA-F]{8}|u[0-9a-fA-F]{4}|x[0-9a-fA-F]{1,2})/
        };
    e.languages.bash = {
        shebang: {pattern: /^#!\s*\/.*/, alias: "important"},
        comment: {pattern: /(^|[^"{\\$])#.*/, lookbehind: !0},
        "function-name": [{
            pattern: /(\bfunction\s+)[\w-]+(?=(?:\s*\(?:\s*\))?\s*\{)/,
            lookbehind: !0,
            alias: "function"
        }, {pattern: /\b[\w-]+(?=\s*\(\s*\)\s*\{)/, alias: "function"}],
        "for-or-select": {pattern: /(\b(?:for|select)\s+)\w+(?=\s+in\s)/, alias: "variable", lookbehind: !0},
        "assign-left": {
            pattern: /(^|[\s;|&]|[<>]\()\w+(?:\.\w+)*(?=\+?=)/,
            inside: {environment: {pattern: RegExp("(^|[\\s;|&]|[<>]\\()" + t), lookbehind: !0, alias: "constant"}},
            alias: "variable",
            lookbehind: !0
        },
        parameter: {pattern: /(^|\s)-{1,2}(?:\w+:[+-]?)?\w+(?:\.\w+)*(?=[=\s]|$)/, alias: "variable", lookbehind: !0},
        string: [{
            pattern: /((?:^|[^<])<<-?\s*)(\w+)\s[\s\S]*?(?:\r?\n|\r)\2/,
            lookbehind: !0,
            greedy: !0,
            inside: n
        }, {
            pattern: /((?:^|[^<])<<-?\s*)(["'])(\w+)\2\s[\s\S]*?(?:\r?\n|\r)\3/,
            lookbehind: !0,
            greedy: !0,
            inside: {bash: a}
        }, {
            pattern: /(^|[^\\](?:\\\\)*)"(?:\\[\s\S]|\$\([^)]+\)|\$(?!\()|`[^`]+`|[^"\\`$])*"/,
            lookbehind: !0,
            greedy: !0,
            inside: n
        }, {pattern: /(^|[^$\\])'[^']*'/, lookbehind: !0, greedy: !0}, {
            pattern: /\$'(?:[^'\\]|\\[\s\S])*'/,
            greedy: !0,
            inside: {entity: n.entity}
        }],
        environment: {pattern: RegExp("\\$?" + t), alias: "constant"},
        variable: n.variable,
        function: {
            pattern: /(^|[\s;|&]|[<>]\()(?:add|apropos|apt|apt-cache|apt-get|aptitude|aspell|automysqlbackup|awk|basename|bash|bc|bconsole|bg|bzip2|cal|cargo|cat|cfdisk|chgrp|chkconfig|chmod|chown|chroot|cksum|clear|cmp|column|comm|composer|cp|cron|crontab|csplit|curl|cut|date|dc|dd|ddrescue|debootstrap|df|diff|diff3|dig|dir|dircolors|dirname|dirs|dmesg|docker|docker-compose|du|egrep|eject|env|ethtool|expand|expect|expr|fdformat|fdisk|fg|fgrep|file|find|fmt|fold|format|free|fsck|ftp|fuser|gawk|git|gparted|grep|groupadd|groupdel|groupmod|groups|grub-mkconfig|gzip|halt|head|hg|history|host|hostname|htop|iconv|id|ifconfig|ifdown|ifup|import|install|ip|java|jobs|join|kill|killall|less|link|ln|locate|logname|logrotate|look|lpc|lpr|lprint|lprintd|lprintq|lprm|ls|lsof|lynx|make|man|mc|mdadm|mkconfig|mkdir|mke2fs|mkfifo|mkfs|mkisofs|mknod|mkswap|mmv|more|most|mount|mtools|mtr|mutt|mv|nano|nc|netstat|nice|nl|node|nohup|notify-send|npm|nslookup|op|open|parted|passwd|paste|pathchk|ping|pkill|pnpm|podman|podman-compose|popd|pr|printcap|printenv|ps|pushd|pv|quota|quotacheck|quotactl|ram|rar|rcp|reboot|remsync|rename|renice|rev|rm|rmdir|rpm|rsync|scp|screen|sdiff|sed|sendmail|seq|service|sftp|sh|shellcheck|shuf|shutdown|sleep|slocate|sort|split|ssh|stat|strace|su|sudo|sum|suspend|swapon|sync|sysctl|tac|tail|tar|tee|time|timeout|top|touch|tr|traceroute|tsort|tty|umount|uname|unexpand|uniq|units|unrar|unshar|unzip|update-grub|uptime|useradd|userdel|usermod|users|uudecode|uuencode|v|vcpkg|vdir|vi|vim|virsh|vmstat|wait|watch|wc|wget|whereis|which|who|whoami|write|xargs|xdg-open|yarn|yes|zenity|zip|zsh|zypper)(?=$|[)\s;|&])/,
            lookbehind: !0
        },
        keyword: {
            pattern: /(^|[\s;|&]|[<>]\()(?:case|do|done|elif|else|esac|fi|for|function|if|in|select|then|until|while)(?=$|[)\s;|&])/,
            lookbehind: !0
        },
        builtin: {
            pattern: /(^|[\s;|&]|[<>]\()(?:\.|:|alias|bind|break|builtin|caller|cd|command|continue|declare|echo|enable|eval|exec|exit|export|getopts|hash|help|let|local|logout|mapfile|printf|pwd|read|readarray|readonly|return|set|shift|shopt|source|test|times|trap|type|typeset|ulimit|umask|unalias|unset)(?=$|[)\s;|&])/,
            lookbehind: !0,
            alias: "class-name"
        },
        boolean: {pattern: /(^|[\s;|&]|[<>]\()(?:false|true)(?=$|[)\s;|&])/, lookbehind: !0},
        "file-descriptor": {pattern: /\B&\d\b/, alias: "important"},
        operator: {
            pattern: /\d?<>|>\||\+=|=[=~]?|!=?|<<[<-]?|[&\d]?>>|\d[<>]&?|[<>][&=]?|&[>&]?|\|[&|]?/,
            inside: {"file-descriptor": {pattern: /^\d/, alias: "important"}}
        },
        punctuation: /\$?\(\(?|\)\)?|\.\.|[{}[\];\\]/,
        number: {pattern: /(^|\s)(?:[1-9]\d*|0)(?:[.,]\d+)?\b/, lookbehind: !0}
    }, a.inside = e.languages.bash;
    for (var s = ["comment", "function-name", "for-or-select", "assign-left", "parameter", "string", "environment", "function", "keyword", "builtin", "boolean", "file-descriptor", "operator", "punctuation", "number"], o = n.variable[1].inside, i = 0; i < s.length; i++) o[s[i]] = e.languages.bash[s[i]];
    e.languages.sh = e.languages.bash, e.languages.shell = e.languages.bash
}(Prism);
!function (e) {
    e.languages.diff = {coord: [/^(?:\*{3}|-{3}|\+{3}).*$/m, /^@@.*@@$/m, /^\d.*$/m]};
    var n = {
        "deleted-sign": "-",
        "deleted-arrow": "<",
        "inserted-sign": "+",
        "inserted-arrow": ">",
        unchanged: " ",
        diff: "!"
    };
    Object.keys(n).forEach((function (a) {
        var i = n[a], r = [];
        /^\w+$/.test(a) || r.push(/\w+/.exec(a)[0]), "diff" === a && r.push("bold"), e.languages.diff[a] = {
            pattern: RegExp("^(?:[" + i + "].*(?:\r\n?|\n|(?![\\s\\S])))+", "m"),
            alias: r,
            inside: {
                line: {pattern: /(.)(?=[\s\S]).*(?:\r\n?|\n)?/, lookbehind: !0},
                prefix: {pattern: /[\s\S]/, alias: /\w+/.exec(a)[0]}
            }
        }
    })), Object.defineProperty(e.languages.diff, "PREFIXES", {value: n})
}(Prism);
!function (e) {
    var n = "(?:[ \t]+(?![ \t])(?:<SP_BS>)?|<SP_BS>)".replace(/<SP_BS>/g, (function () {
            return "\\\\[\r\n](?:\\s|\\\\[\r\n]|#.*(?!.))*(?![\\s#]|\\\\[\r\n])"
        })), r = "\"(?:[^\"\\\\\r\n]|\\\\(?:\r\n|[^]))*\"|'(?:[^'\\\\\r\n]|\\\\(?:\r\n|[^]))*'",
        t = "--[\\w-]+=(?:<STR>|(?![\"'])(?:[^\\s\\\\]|\\\\.)+)".replace(/<STR>/g, (function () {
            return r
        })), o = {pattern: RegExp(r), greedy: !0}, i = {pattern: /(^[ \t]*)#.*/m, lookbehind: !0, greedy: !0};

    function a(e, r) {
        return e = e.replace(/<OPT>/g, (function () {
            return t
        })).replace(/<SP>/g, (function () {
            return n
        })), RegExp(e, r)
    }

    e.languages.docker = {
        instruction: {
            pattern: /(^[ \t]*)(?:ADD|ARG|CMD|COPY|ENTRYPOINT|ENV|EXPOSE|FROM|HEALTHCHECK|LABEL|MAINTAINER|ONBUILD|RUN|SHELL|STOPSIGNAL|USER|VOLUME|WORKDIR)(?=\s)(?:\\.|[^\r\n\\])*(?:\\$(?:\s|#.*$)*(?![\s#])(?:\\.|[^\r\n\\])*)*/im,
            lookbehind: !0,
            greedy: !0,
            inside: {
                options: {
                    pattern: a("(^(?:ONBUILD<SP>)?\\w+<SP>)<OPT>(?:<SP><OPT>)*", "i"),
                    lookbehind: !0,
                    greedy: !0,
                    inside: {
                        property: {pattern: /(^|\s)--[\w-]+/, lookbehind: !0},
                        string: [o, {pattern: /(=)(?!["'])(?:[^\s\\]|\\.)+/, lookbehind: !0}],
                        operator: /\\$/m,
                        punctuation: /=/
                    }
                },
                keyword: [{
                    pattern: a("(^(?:ONBUILD<SP>)?HEALTHCHECK<SP>(?:<OPT><SP>)*)(?:CMD|NONE)\\b", "i"),
                    lookbehind: !0,
                    greedy: !0
                }, {
                    pattern: a("(^(?:ONBUILD<SP>)?FROM<SP>(?:<OPT><SP>)*(?!--)[^ \t\\\\]+<SP>)AS", "i"),
                    lookbehind: !0,
                    greedy: !0
                }, {pattern: a("(^ONBUILD<SP>)\\w+", "i"), lookbehind: !0, greedy: !0}, {pattern: /^\w+/, greedy: !0}],
                comment: i,
                string: o,
                variable: /\$(?:\w+|\{[^{}"'\\]*\})/,
                operator: /\\$/m
            }
        }, comment: i
    }, e.languages.dockerfile = e.languages.docker
}(Prism);
!function (n) {
    n.languages.kotlin = n.languages.extend("clike", {
        keyword: {
            pattern: /(^|[^.])\b(?:abstract|actual|annotation|as|break|by|catch|class|companion|const|constructor|continue|crossinline|data|do|dynamic|else|enum|expect|external|final|finally|for|fun|get|if|import|in|infix|init|inline|inner|interface|internal|is|lateinit|noinline|null|object|open|operator|out|override|package|private|protected|public|reified|return|sealed|set|super|suspend|tailrec|this|throw|to|try|typealias|val|var|vararg|when|where|while)\b/,
            lookbehind: !0
        },
        function: [{
            pattern: /(?:`[^\r\n`]+`|\b\w+)(?=\s*\()/,
            greedy: !0
        }, {pattern: /(\.)(?:`[^\r\n`]+`|\w+)(?=\s*\{)/, lookbehind: !0, greedy: !0}],
        number: /\b(?:0[xX][\da-fA-F]+(?:_[\da-fA-F]+)*|0[bB][01]+(?:_[01]+)*|\d+(?:_\d+)*(?:\.\d+(?:_\d+)*)?(?:[eE][+-]?\d+(?:_\d+)*)?[fFL]?)\b/,
        operator: /\+[+=]?|-[-=>]?|==?=?|!(?:!|==?)?|[\/*%<>]=?|[?:]:?|\.\.|&&|\|\||\b(?:and|inv|or|shl|shr|ushr|xor)\b/
    }), delete n.languages.kotlin["class-name"];
    var e = {
        "interpolation-punctuation": {pattern: /^\$\{?|\}$/, alias: "punctuation"},
        expression: {pattern: /[\s\S]+/, inside: n.languages.kotlin}
    };
    n.languages.insertBefore("kotlin", "string", {
        "string-literal": [{
            pattern: /"""(?:[^$]|\$(?:(?!\{)|\{[^{}]*\}))*?"""/,
            alias: "multiline",
            inside: {interpolation: {pattern: /\$(?:[a-z_]\w*|\{[^{}]*\})/i, inside: e}, string: /[\s\S]+/}
        }, {
            pattern: /"(?:[^"\\\r\n$]|\\.|\$(?:(?!\{)|\{[^{}]*\}))*"/,
            alias: "singleline",
            inside: {
                interpolation: {
                    pattern: /((?:^|[^\\])(?:\\{2})*)\$(?:[a-z_]\w*|\{[^{}]*\})/i,
                    lookbehind: !0,
                    inside: e
                }, string: /[\s\S]+/
            }
        }], char: {pattern: /'(?:[^'\\\r\n]|\\(?:.|u[a-fA-F0-9]{0,4}))'/, greedy: !0}
    }), delete n.languages.kotlin.string, n.languages.insertBefore("kotlin", "keyword", {
        annotation: {
            pattern: /\B@(?:\w+:)?(?:[A-Z]\w*|\[[^\]]+\])/,
            alias: "builtin"
        }
    }), n.languages.insertBefore("kotlin", "function", {
        label: {
            pattern: /\b\w+@|@\w+\b/,
            alias: "symbol"
        }
    }), n.languages.kt = n.languages.kotlin, n.languages.kts = n.languages.kotlin
}(Prism);
!function (n) {
    function e(n) {
        return n = n.replace(/<inner>/g, (function () {
            return "(?:\\\\.|[^\\\\\n\r]|(?:\n|\r\n?)(?![\r\n]))"
        })), RegExp("((?:^|[^\\\\])(?:\\\\{2})*)(?:" + n + ")")
    }

    var t = "(?:\\\\.|``(?:[^`\r\n]|`(?!`))+``|`[^`\r\n]+`|[^\\\\|\r\n`])+",
        a = "\\|?__(?:\\|__)+\\|?(?:(?:\n|\r\n?)|(?![^]))".replace(/__/g, (function () {
            return t
        })), i = "\\|?[ \t]*:?-{3,}:?[ \t]*(?:\\|[ \t]*:?-{3,}:?[ \t]*)+\\|?(?:\n|\r\n?)";
    n.languages.markdown = n.languages.extend("markup", {}), n.languages.insertBefore("markdown", "prolog", {
        "front-matter-block": {
            pattern: /(^(?:\s*[\r\n])?)---(?!.)[\s\S]*?[\r\n]---(?!.)/,
            lookbehind: !0,
            greedy: !0,
            inside: {
                punctuation: /^---|---$/,
                "front-matter": {pattern: /\S+(?:\s+\S+)*/, alias: ["yaml", "language-yaml"], inside: n.languages.yaml}
            }
        },
        blockquote: {pattern: /^>(?:[\t ]*>)*/m, alias: "punctuation"},
        table: {
            pattern: RegExp("^" + a + i + "(?:" + a + ")*", "m"),
            inside: {
                "table-data-rows": {
                    pattern: RegExp("^(" + a + i + ")(?:" + a + ")*$"),
                    lookbehind: !0,
                    inside: {"table-data": {pattern: RegExp(t), inside: n.languages.markdown}, punctuation: /\|/}
                },
                "table-line": {
                    pattern: RegExp("^(" + a + ")" + i + "$"),
                    lookbehind: !0,
                    inside: {punctuation: /\||:?-{3,}:?/}
                },
                "table-header-row": {
                    pattern: RegExp("^" + a + "$"),
                    inside: {
                        "table-header": {pattern: RegExp(t), alias: "important", inside: n.languages.markdown},
                        punctuation: /\|/
                    }
                }
            }
        },
        code: [{
            pattern: /((?:^|\n)[ \t]*\n|(?:^|\r\n?)[ \t]*\r\n?)(?: {4}|\t).+(?:(?:\n|\r\n?)(?: {4}|\t).+)*/,
            lookbehind: !0,
            alias: "keyword"
        }, {
            pattern: /^```[\s\S]*?^```$/m,
            greedy: !0,
            inside: {
                "code-block": {pattern: /^(```.*(?:\n|\r\n?))[\s\S]+?(?=(?:\n|\r\n?)^```$)/m, lookbehind: !0},
                "code-language": {pattern: /^(```).+/, lookbehind: !0},
                punctuation: /```/
            }
        }],
        title: [{
            pattern: /\S.*(?:\n|\r\n?)(?:==+|--+)(?=[ \t]*$)/m,
            alias: "important",
            inside: {punctuation: /==+$|--+$/}
        }, {pattern: /(^\s*)#.+/m, lookbehind: !0, alias: "important", inside: {punctuation: /^#+|#+$/}}],
        hr: {pattern: /(^\s*)([*-])(?:[\t ]*\2){2,}(?=\s*$)/m, lookbehind: !0, alias: "punctuation"},
        list: {pattern: /(^\s*)(?:[*+-]|\d+\.)(?=[\t ].)/m, lookbehind: !0, alias: "punctuation"},
        "url-reference": {
            pattern: /!?\[[^\]]+\]:[\t ]+(?:\S+|<(?:\\.|[^>\\])+>)(?:[\t ]+(?:"(?:\\.|[^"\\])*"|'(?:\\.|[^'\\])*'|\((?:\\.|[^)\\])*\)))?/,
            inside: {
                variable: {pattern: /^(!?\[)[^\]]+/, lookbehind: !0},
                string: /(?:"(?:\\.|[^"\\])*"|'(?:\\.|[^'\\])*'|\((?:\\.|[^)\\])*\))$/,
                punctuation: /^[\[\]!:]|[<>]/
            },
            alias: "url"
        },
        bold: {
            pattern: e("\\b__(?:(?!_)<inner>|_(?:(?!_)<inner>)+_)+__\\b|\\*\\*(?:(?!\\*)<inner>|\\*(?:(?!\\*)<inner>)+\\*)+\\*\\*"),
            lookbehind: !0,
            greedy: !0,
            inside: {content: {pattern: /(^..)[\s\S]+(?=..$)/, lookbehind: !0, inside: {}}, punctuation: /\*\*|__/}
        },
        italic: {
            pattern: e("\\b_(?:(?!_)<inner>|__(?:(?!_)<inner>)+__)+_\\b|\\*(?:(?!\\*)<inner>|\\*\\*(?:(?!\\*)<inner>)+\\*\\*)+\\*"),
            lookbehind: !0,
            greedy: !0,
            inside: {content: {pattern: /(^.)[\s\S]+(?=.$)/, lookbehind: !0, inside: {}}, punctuation: /[*_]/}
        },
        strike: {
            pattern: e("(~~?)(?:(?!~)<inner>)+\\2"),
            lookbehind: !0,
            greedy: !0,
            inside: {content: {pattern: /(^~~?)[\s\S]+(?=\1$)/, lookbehind: !0, inside: {}}, punctuation: /~~?/}
        },
        "code-snippet": {
            pattern: /(^|[^\\`])(?:``[^`\r\n]+(?:`[^`\r\n]+)*``(?!`)|`[^`\r\n]+`(?!`))/,
            lookbehind: !0,
            greedy: !0,
            alias: ["code", "keyword"]
        },
        url: {
            pattern: e('!?\\[(?:(?!\\])<inner>)+\\](?:\\([^\\s)]+(?:[\t ]+"(?:\\\\.|[^"\\\\])*")?\\)|[ \t]?\\[(?:(?!\\])<inner>)+\\])'),
            lookbehind: !0,
            greedy: !0,
            inside: {
                operator: /^!/,
                content: {pattern: /(^\[)[^\]]+(?=\])/, lookbehind: !0, inside: {}},
                variable: {pattern: /(^\][ \t]?\[)[^\]]+(?=\]$)/, lookbehind: !0},
                url: {pattern: /(^\]\()[^\s)]+/, lookbehind: !0},
                string: {pattern: /(^[ \t]+)"(?:\\.|[^"\\])*"(?=\)$)/, lookbehind: !0}
            }
        }
    }), ["url", "bold", "italic", "strike"].forEach((function (e) {
        ["url", "bold", "italic", "strike", "code-snippet"].forEach((function (t) {
            e !== t && (n.languages.markdown[e].inside.content.inside[t] = n.languages.markdown[t])
        }))
    })), n.hooks.add("after-tokenize", (function (n) {
        "markdown" !== n.language && "md" !== n.language || function n(e) {
            if (e && "string" != typeof e) for (var t = 0, a = e.length; t < a; t++) {
                var i = e[t];
                if ("code" === i.type) {
                    var r = i.content[1], o = i.content[3];
                    if (r && o && "code-language" === r.type && "code-block" === o.type && "string" == typeof r.content) {
                        var l = r.content.replace(/\b#/g, "sharp").replace(/\b\+\+/g, "pp"),
                            s = "language-" + (l = (/[a-z][\w-]*/i.exec(l) || [""])[0].toLowerCase());
                        o.alias ? "string" == typeof o.alias ? o.alias = [o.alias, s] : o.alias.push(s) : o.alias = [s]
                    }
                } else n(i.content)
            }
        }(n.tokens)
    })), n.hooks.add("wrap", (function (e) {
        if ("code-block" === e.type) {
            for (var t = "", a = 0, i = e.classes.length; a < i; a++) {
                var s = e.classes[a], d = /language-(.+)/.exec(s);
                if (d) {
                    t = d[1];
                    break
                }
            }
            var p = n.languages[t];
            if (p) e.content = n.highlight(e.content.replace(r, "").replace(/&(\w{1,8}|#x?[\da-f]{1,8});/gi, (function (n, e) {
                var t;
                return "#" === (e = e.toLowerCase())[0] ? (t = "x" === e[1] ? parseInt(e.slice(2), 16) : Number(e.slice(1)), l(t)) : o[e] || n
            })), p, t); else if (t && "none" !== t && n.plugins.autoloader) {
                var u = "md-" + (new Date).valueOf() + "-" + Math.floor(1e16 * Math.random());
                e.attributes.id = u, n.plugins.autoloader.loadLanguages(t, (function () {
                    var e = document.getElementById(u);
                    e && (e.innerHTML = n.highlight(e.textContent, n.languages[t], t))
                }))
            }
        }
    }));
    var r = RegExp(n.languages.markup.tag.pattern.source, "gi"), o = {amp: "&", lt: "<", gt: ">", quot: '"'},
        l = String.fromCodePoint || String.fromCharCode;
    n.languages.md = n.languages.markdown
}(Prism)
!function (e) {
    function n(e) {
        return e.replace(/__/g, (function () {
            return "(?:[\\w-]+|'[^'\n\r]*'|\"(?:\\\\.|[^\\\\\"\r\n])*\")"
        }))
    }

    e.languages.toml = {
        comment: {pattern: /#.*/, greedy: !0},
        table: {
            pattern: RegExp(n("(^[\t ]*\\[\\s*(?:\\[\\s*)?)__(?:\\s*\\.\\s*__)*(?=\\s*\\])"), "m"),
            lookbehind: !0,
            greedy: !0,
            alias: "class-name"
        },
        key: {
            pattern: RegExp(n("(^[\t ]*|[{,]\\s*)__(?:\\s*\\.\\s*__)*(?=\\s*=)"), "m"),
            lookbehind: !0,
            greedy: !0,
            alias: "property"
        },
        string: {pattern: /"""(?:\\[\s\S]|[^\\])*?"""|'''[\s\S]*?'''|'[^'\n\r]*'|"(?:\\.|[^\\"\r\n])*"/, greedy: !0},
        date: [{
            pattern: /\b\d{4}-\d{2}-\d{2}(?:[T\s]\d{2}:\d{2}:\d{2}(?:\.\d+)?(?:Z|[+-]\d{2}:\d{2})?)?\b/i,
            alias: "number"
        }, {pattern: /\b\d{2}:\d{2}:\d{2}(?:\.\d+)?\b/, alias: "number"}],
        number: /(?:\b0(?:x[\da-zA-Z]+(?:_[\da-zA-Z]+)*|o[0-7]+(?:_[0-7]+)*|b[10]+(?:_[10]+)*))\b|[-+]?\b\d+(?:_\d+)*(?:\.\d+(?:_\d+)*)?(?:[eE][+-]?\d+(?:_\d+)*)?\b|[-+]?\b(?:inf|nan)\b/,
        boolean: /\b(?:false|true)\b/,
        punctuation: /[.,=[\]{}]/
    }
}(Prism);
!function (e) {
    e.languages.typescript = e.languages.extend("javascript", {
        "class-name": {
            pattern: /(\b(?:class|extends|implements|instanceof|interface|new|type)\s+)(?!keyof\b)(?!\s)[_$a-zA-Z\xA0-\uFFFF](?:(?!\s)[$\w\xA0-\uFFFF])*(?:\s*<(?:[^<>]|<(?:[^<>]|<[^<>]*>)*>)*>)?/,
            lookbehind: !0,
            greedy: !0,
            inside: null
        }, builtin: /\b(?:Array|Function|Promise|any|boolean|console|never|number|string|symbol|unknown)\b/
    }), e.languages.typescript.keyword.push(/\b(?:abstract|declare|is|keyof|readonly|require)\b/, /\b(?:asserts|infer|interface|module|namespace|type)\b(?=\s*(?:[{_$a-zA-Z\xA0-\uFFFF]|$))/, /\btype\b(?=\s*(?:[\{*]|$))/), delete e.languages.typescript.parameter, delete e.languages.typescript["literal-property"];
    var s = e.languages.extend("typescript", {});
    delete s["class-name"], e.languages.typescript["class-name"].inside = s, e.languages.insertBefore("typescript", "function", {
        decorator: {
            pattern: /@[$\w\xA0-\uFFFF]+/,
            inside: {at: {pattern: /^@/, alias: "operator"}, function: /^[\s\S]+/}
        },
        "generic-function": {
            pattern: /#?(?!\s)[_$a-zA-Z\xA0-\uFFFF](?:(?!\s)[$\w\xA0-\uFFFF])*\s*<(?:[^<>]|<(?:[^<>]|<[^<>]*>)*>)*>(?=\s*\()/,
            greedy: !0,
            inside: {
                function: /^#?(?!\s)[_$a-zA-Z\xA0-\uFFFF](?:(?!\s)[$\w\xA0-\uFFFF])*/,
                generic: {pattern: /<[\s\S]+/, alias: "class-name", inside: s}
            }
        }
    }), e.languages.ts = e.languages.typescript
}(Prism);
!function (e) {
    var n = /[*&][^\s[\]{},]+/, r = /!(?:<[\w\-%#;/?:@&=+$,.!~*'()[\]]+>|(?:[a-zA-Z\d-]*!)?[\w\-%#;/?:@&=+$.~*'()]+)?/,
        t = "(?:" + r.source + "(?:[ \t]+" + n.source + ")?|" + n.source + "(?:[ \t]+" + r.source + ")?)",
        a = "(?:[^\\s\\x00-\\x08\\x0e-\\x1f!\"#%&'*,\\-:>?@[\\]`{|}\\x7f-\\x84\\x86-\\x9f\\ud800-\\udfff\\ufffe\\uffff]|[?:-]<PLAIN>)(?:[ \t]*(?:(?![#:])<PLAIN>|:<PLAIN>))*".replace(/<PLAIN>/g, (function () {
            return "[^\\s\\x00-\\x08\\x0e-\\x1f,[\\]{}\\x7f-\\x84\\x86-\\x9f\\ud800-\\udfff\\ufffe\\uffff]"
        })), d = "\"(?:[^\"\\\\\r\n]|\\\\.)*\"|'(?:[^'\\\\\r\n]|\\\\.)*'";

    function o(e, n) {
        n = (n || "").replace(/m/g, "") + "m";
        var r = "([:\\-,[{]\\s*(?:\\s<<prop>>[ \t]+)?)(?:<<value>>)(?=[ \t]*(?:$|,|\\]|\\}|(?:[\r\n]\\s*)?#))".replace(/<<prop>>/g, (function () {
            return t
        })).replace(/<<value>>/g, (function () {
            return e
        }));
        return RegExp(r, n)
    }

    e.languages.yaml = {
        scalar: {
            pattern: RegExp("([\\-:]\\s*(?:\\s<<prop>>[ \t]+)?[|>])[ \t]*(?:((?:\r?\n|\r)[ \t]+)\\S[^\r\n]*(?:\\2[^\r\n]+)*)".replace(/<<prop>>/g, (function () {
                return t
            }))), lookbehind: !0, alias: "string"
        },
        comment: /#.*/,
        key: {
            pattern: RegExp("((?:^|[:\\-,[{\r\n?])[ \t]*(?:<<prop>>[ \t]+)?)<<key>>(?=\\s*:\\s)".replace(/<<prop>>/g, (function () {
                return t
            })).replace(/<<key>>/g, (function () {
                return "(?:" + a + "|" + d + ")"
            }))), lookbehind: !0, greedy: !0, alias: "atrule"
        },
        directive: {pattern: /(^[ \t]*)%.+/m, lookbehind: !0, alias: "important"},
        datetime: {
            pattern: o("\\d{4}-\\d\\d?-\\d\\d?(?:[tT]|[ \t]+)\\d\\d?:\\d{2}:\\d{2}(?:\\.\\d*)?(?:[ \t]*(?:Z|[-+]\\d\\d?(?::\\d{2})?))?|\\d{4}-\\d{2}-\\d{2}|\\d\\d?:\\d{2}(?::\\d{2}(?:\\.\\d*)?)?"),
            lookbehind: !0,
            alias: "number"
        },
        boolean: {pattern: o("false|true", "i"), lookbehind: !0, alias: "important"},
        null: {pattern: o("null|~", "i"), lookbehind: !0, alias: "important"},
        string: {pattern: o(d), lookbehind: !0, greedy: !0},
        number: {
            pattern: o("[+-]?(?:0x[\\da-f]+|0o[0-7]+|(?:\\d+(?:\\.\\d*)?|\\.\\d+)(?:e[+-]?\\d+)?|\\.inf|\\.nan)", "i"),
            lookbehind: !0
        },
        tag: r,
        important: n,
        punctuation: /---|[:[\]{}\-,|>?]|\.\.\./
    }, e.languages.yml = e.languages.yaml
}(Prism);
!function () {
    if ("undefined" != typeof Prism && "undefined" != typeof document && document.querySelector) {
        var e, t = "line-numbers", i = "linkable-line-numbers", n = /\n(?!$)/g, r = !0;
        Prism.plugins.lineHighlight = {
            highlightLines: function (o, u, c) {
                var h = (u = "string" == typeof u ? u : o.getAttribute("data-line") || "").replace(/\s+/g, "").split(",").filter(Boolean),
                    d = +o.getAttribute("data-line-offset") || 0, f = (function () {
                        if (void 0 === e) {
                            var t = document.createElement("div");
                            t.style.fontSize = "13px", t.style.lineHeight = "1.5", t.style.padding = "0", t.style.border = "0", t.innerHTML = "&nbsp;<br />&nbsp;", document.body.appendChild(t), e = 38 === t.offsetHeight, document.body.removeChild(t)
                        }
                        return e
                    }() ? parseInt : parseFloat)(getComputedStyle(o).lineHeight), p = Prism.util.isActive(o, t),
                    g = o.querySelector("code"), m = p ? o : g || o, v = [], y = g.textContent.match(n),
                    b = y ? y.length + 1 : 1, A = g && m != g ? function (e, t) {
                        var i = getComputedStyle(e), n = getComputedStyle(t);

                        function r(e) {
                            return +e.substr(0, e.length - 2)
                        }

                        return t.offsetTop + r(n.borderTopWidth) + r(n.paddingTop) - r(i.paddingTop)
                    }(o, g) : 0;
                h.forEach((function (e) {
                    var t = e.split("-"), i = +t[0], n = +t[1] || i;
                    if (!((n = Math.min(b + d, n)) < i)) {
                        var r = o.querySelector('.line-highlight[data-range="' + e + '"]') || document.createElement("div");
                        if (v.push((function () {
                            r.setAttribute("aria-hidden", "true"), r.setAttribute("data-range", e), r.className = (c || "") + " line-highlight"
                        })), p && Prism.plugins.lineNumbers) {
                            var s = Prism.plugins.lineNumbers.getLine(o, i),
                                l = Prism.plugins.lineNumbers.getLine(o, n);
                            if (s) {
                                var a = s.offsetTop + A + "px";
                                v.push((function () {
                                    r.style.top = a
                                }))
                            }
                            if (l) {
                                var u = l.offsetTop - s.offsetTop + l.offsetHeight + "px";
                                v.push((function () {
                                    r.style.height = u
                                }))
                            }
                        } else v.push((function () {
                            r.setAttribute("data-start", String(i)), n > i && r.setAttribute("data-end", String(n)), r.style.top = (i - d - 1) * f + A + "px", r.textContent = new Array(n - i + 2).join(" \n")
                        }));
                        v.push((function () {
                            r.style.width = o.scrollWidth + "px"
                        })), v.push((function () {
                            m.appendChild(r)
                        }))
                    }
                }));
                var P = o.id;
                if (p && Prism.util.isActive(o, i) && P) {
                    l(o, i) || v.push((function () {
                        o.classList.add(i)
                    }));
                    var E = parseInt(o.getAttribute("data-start") || "1");
                    s(".line-numbers-rows > span", o).forEach((function (e, t) {
                        var i = t + E;
                        e.onclick = function () {
                            var e = P + "." + i;
                            r = !1, location.hash = e, setTimeout((function () {
                                r = !0
                            }), 1)
                        }
                    }))
                }
                return function () {
                    v.forEach(a)
                }
            }
        };
        var o = 0;
        Prism.hooks.add("before-sanity-check", (function (e) {
            var t = e.element.parentElement;
            if (u(t)) {
                var i = 0;
                s(".line-highlight", t).forEach((function (e) {
                    i += e.textContent.length, e.parentNode.removeChild(e)
                })), i && /^(?: \n)+$/.test(e.code.slice(-i)) && (e.code = e.code.slice(0, -i))
            }
        })), Prism.hooks.add("complete", (function e(i) {
            var n = i.element.parentElement;
            if (u(n)) {
                clearTimeout(o);
                var r = Prism.plugins.lineNumbers, s = i.plugins && i.plugins.lineNumbers;
                l(n, t) && r && !s ? Prism.hooks.add("line-numbers", e) : (Prism.plugins.lineHighlight.highlightLines(n)(), o = setTimeout(c, 1))
            }
        })), window.addEventListener("hashchange", c), window.addEventListener("resize", (function () {
            s("pre").filter(u).map((function (e) {
                return Prism.plugins.lineHighlight.highlightLines(e)
            })).forEach(a)
        }))
    }

    function s(e, t) {
        return Array.prototype.slice.call((t || document).querySelectorAll(e))
    }

    function l(e, t) {
        return e.classList.contains(t)
    }

    function a(e) {
        e()
    }

    function u(e) {
        return !!(e && /pre/i.test(e.nodeName) && (e.hasAttribute("data-line") || e.id && Prism.util.isActive(e, i)))
    }

    function c() {
        var e = location.hash.slice(1);
        s(".temporary.line-highlight").forEach((function (e) {
            e.parentNode.removeChild(e)
        }));
        var t = (e.match(/\.([\d,-]+)$/) || [, ""])[1];
        if (t && !document.getElementById(e)) {
            var i = e.slice(0, e.lastIndexOf(".")), n = document.getElementById(i);
            n && (n.hasAttribute("data-line") || n.setAttribute("data-line", ""), Prism.plugins.lineHighlight.highlightLines(n, t, "temporary ")(), r && document.querySelector(".temporary.line-highlight").scrollIntoView())
        }
    }
}();
!function () {
    function t(t) {
        var e = document.createElement("textarea");
        e.value = t.getText(), e.style.top = "0", e.style.left = "0", e.style.position = "fixed", document.body.appendChild(e), e.focus(), e.select();
        try {
            var o = document.execCommand("copy");
            setTimeout((function () {
                o ? t.success() : t.error()
            }), 1)
        } catch (e) {
            setTimeout((function () {
                t.error(e)
            }), 1)
        }
        document.body.removeChild(e)
    }
}();
!function () {
    if ("undefined" != typeof Prism) {
        var e = /^diff-([\w-]+)/i,
            i = /<\/?(?!\d)[^\s>\/=$<%]+(?:\s(?:\s*[^\s>\/=]+(?:\s*=\s*(?:"[^"]*"|'[^']*'|[^\s'">=]+(?=[\s>]))|(?=[\s/>])))+)?\s*\/?>/g,
            a = RegExp("(?:__|[^\r\n<])*(?:\r\n?|\n|(?:__|[^\r\n<])(?![^\r\n]))".replace(/__/g, (function () {
                return i.source
            })), "gi"), s = !1;
        Prism.hooks.add("before-sanity-check", (function (i) {
            var a = i.language;
            e.test(a) && !i.grammar && (i.grammar = Prism.languages[a] = Prism.languages.diff)
        })), Prism.hooks.add("before-tokenize", (function (i) {
            s || Prism.languages.diff || Prism.plugins.autoloader || (s = !0, console.warn("Prism's Diff Highlight plugin requires the Diff language definition (prism-diff.js).Make sure the language definition is loaded or use Prism's Autoloader plugin."));
            var a = i.language;
            e.test(a) && !Prism.languages[a] && (Prism.languages[a] = Prism.languages.diff)
        })), Prism.hooks.add("wrap", (function (s) {
            var r, n;
            if ("diff" !== s.language) {
                var g = e.exec(s.language);
                if (!g) return;
                r = g[1], n = Prism.languages[r]
            }
            var f = Prism.languages.diff && Prism.languages.diff.PREFIXES;
            if (f && s.type in f) {
                var u, l = s.content.replace(i, "").replace(/&lt;/g, "<").replace(/&amp;/g, "&"),
                    t = l.replace(/(^|[\r\n])./g, "$1");
                u = n ? Prism.highlight(t, n, r) : Prism.util.encode(t);
                var o, m = new Prism.Token("prefix", f[s.type], [/\w+/.exec(s.type)[0]]),
                    d = Prism.Token.stringify(m, s.language), c = [];
                for (a.lastIndex = 0; o = a.exec(u);) c.push(d + o[0]);
                /(?:^|[\r\n]).$/.test(l) && c.push(d), s.content = c.join(""), n && s.classes.push("language-" + r)
            }
        }))
    }
}();