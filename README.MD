# Compilador ALV

Un compilador básico con interfaz gráfica desarrollado en Java que implementa análisis léxico, sintáctico y semántico.

## 📋 Características principales

El compilador implementa un subconjunto de instrucciones de programación, principalmente:

- Declaraciones de variables
- Asignaciones
- Expresiones aritméticas básicas
- Validación de jerarquía de paréntesis, corchetes y llaves

## ✅ Lo que VALIDA el compilador

### 1. Declaraciones de variables

```
int x;
float y;
```

### 2. Asignaciones simples

```
x = 10;
```

### 3. Operaciones aritméticas básicas

```
x = a + b;
y = c - d;
z = e * f;
w = g / h;
```

### 4. Jerarquía con paréntesis, corchetes y llaves

```
x = (a + b) * c;
y = [d + e] / f;
z = {g - h} + i;
```

### 5. Expresiones aritméticas anidadas

```
x = a + (b * (c + d));
y = [a * b] + (c / d);
```

### 6. Validaciones semánticas

- Variables usadas sin declarar previamente
- Variables declaradas más de una vez

## ❌ Lo que NO VALIDA el compilador

### 1. Estructuras de control

```
if (x > 5) { ... }  // No soportado
while (i < 10) { ... }  // No soportado
for (i=0; i<10; i++) { ... }  // No soportado
```

### 2. Operadores relacionales

```
x = a > b;  // No soportado
y = c <= d;  // No soportado
```

### 3. Operaciones lógicas

```
z = true && false;  // No soportado
w = a || b;  // No soportado
```

### 4. Definición de funciones

```
function suma(a, b) { ... }  // No soportado
```

### 5. Llamadas a funciones

```
resultado = calcular(x, y);  // No soportado
```

## ⚠️ Limitaciones actuales

El compilador está diseñado con fines educativos y tiene varias limitaciones:

1. Solo valida asignaciones y declaraciones simples
2. La jerarquía de operaciones solo se controla mediante paréntesis, corchetes y llaves
3. No hay soporte para tipos de datos complejos (solo int y float)
4. No implementa generación de código o ejecución

## 📝 Ejemplos válidos

```
int a;
int b;
a = 10;
b = 5;
int resultado;
resultado = (a + b) * 2;
```

## 🔴 Ejemplos con errores

```
// Error semántico: variable no declarada
x = 10;

// Error semántico: variable declarada dos veces
int y;
int y;

// Error sintáctico: falta punto y coma
a = 5

// Error sintáctico: paréntesis sin cerrar
b = (1 + 2 * 3;

// Error léxico: carácter no reconocido
c = a @ b;
```
