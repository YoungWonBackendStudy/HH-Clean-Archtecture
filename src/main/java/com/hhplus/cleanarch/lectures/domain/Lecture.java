package com.hhplus.cleanarch.lectures.domain;

import java.util.Set;

public record Lecture(
    Set<Long> userIds
) { 
    
}
