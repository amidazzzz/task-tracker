package org.amida.backend.response;

import lombok.Builder;

@Builder
public record ApiResponse(String message, boolean success, String token){}
