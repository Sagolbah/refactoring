package ru.akirakozov.sd.refactoring.util;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public final class HtmlUtils {
    public static void renderHtmlPage(final HttpServletResponse response, final String text) throws IOException {
        response.getWriter().println("<html><body>" + text + "</body></html>");
    }

    public static void renderPlaintext(final HttpServletResponse response, final String text) throws IOException {
        response.getWriter().println(text);
    }
}
