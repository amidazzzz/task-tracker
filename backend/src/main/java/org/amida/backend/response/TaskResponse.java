package org.amida.backend.response;

import lombok.Builder;

@Builder
public record TaskResponse(String message, boolean success) {
}
