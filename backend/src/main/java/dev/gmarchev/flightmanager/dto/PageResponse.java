package dev.gmarchev.flightmanager.dto;

import java.util.List;

public record PageResponse<T>(List<T> content, boolean hasNext) {}
