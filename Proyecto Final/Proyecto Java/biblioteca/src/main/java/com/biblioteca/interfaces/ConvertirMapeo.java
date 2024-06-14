package com.biblioteca.interfaces;

import java.util.List;
import java.util.HashMap;

public interface ConvertirMapeo {
    <T> List<T> convertirMapeo(List<HashMap<String, Object>> lista);
} 
