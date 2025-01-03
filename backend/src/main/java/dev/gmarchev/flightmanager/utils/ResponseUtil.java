package dev.gmarchev.flightmanager.utils;

import java.time.LocalDateTime;
import java.util.List;

public class ResponseUtil {

	record ErrorResponse(LocalDateTime timestamp, int status, String error, String path, List<String> details) {}
}
