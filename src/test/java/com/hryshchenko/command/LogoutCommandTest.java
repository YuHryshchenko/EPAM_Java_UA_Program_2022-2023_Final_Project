package com.hryshchenko.command;

import com.hryshchenko.constant.PagesConst;
import org.junit.jupiter.api.Test;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.*;

public class LogoutCommandTest {

    @Test
    void testExecute() throws Exception {
        // Create a mock HttpServletRequest, HttpServletResponse, and HttpSession
        HttpServletRequest request = mock(HttpServletRequest.class);
        HttpServletResponse response = mock(HttpServletResponse.class);
        HttpSession session = mock(HttpSession.class);

        // Set the session attribute to a non-null value
        when(request.getSession()).thenReturn(session);
        when(request.getSession(false)).thenReturn(session);

        // Create a new LogoutCommand object and call its execute method
        LogoutCommand command = new LogoutCommand();
        String result = command.execute(request, response);

        // Verify that the session was invalidated and the log message was printed
        verify(session).invalidate();

        // Verify that the returned address is the expected value
        assertEquals(result, PagesConst.INDEX);
    }

}
