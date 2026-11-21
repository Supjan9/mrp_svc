package org.mrp.controller;

import lombok.Getter;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Route {
    private static final Pattern PARAM_PATTERN = Pattern.compile("\\{([^/]+)}");
    private static final String SEGMENT_REGEX = "([^/]+)";
    private static final String REGEX_START = "^";
    private static final String REGEX_END = "$";

    private final String method;
    private final Pattern pathPattern;
    private final List<String> paramNames;
    @Getter
    private final RouteHandler handler;

    public Route(String method, String pathTemplate, RouteHandler handler) {
        this.method = method;
        this.handler = handler;
        this.paramNames = new ArrayList<>();
        this.pathPattern = compilePattern(pathTemplate);
    }

    private Pattern compilePattern(String pathTemplate) {
        Matcher matcher = PARAM_PATTERN.matcher(pathTemplate);
        StringBuilder regex = new StringBuilder(REGEX_START);
        int lastEnd = 0;

        while (matcher.find()) {
            regex.append(Pattern.quote(pathTemplate.substring(lastEnd, matcher.start())));
            paramNames.add(matcher.group(1));
            regex.append(SEGMENT_REGEX);
            lastEnd = matcher.end();
        }

        regex.append(Pattern.quote(pathTemplate.substring(lastEnd)));
        regex.append(REGEX_END);
        return Pattern.compile(regex.toString());
    }

    public boolean matchesMethod(String requestMethod) {
        return this.method.equalsIgnoreCase(requestMethod);
    }

    public Map<String, String> matchPath(String requestPath) {
        Matcher matcher = pathPattern.matcher(requestPath);
        if (!matcher.matches()) {
            return null;
        }

        Map<String, String> params = new HashMap<>();
        for (int i = 0; i < paramNames.size(); i++) {
            params.put(paramNames.get(i), matcher.group(i + 1));
        }
        return params;
    }

}