CREATE TABLE empleado (
    id INTEGER GENERATED ALWAYS AS IDENTITY PRIMARY KEY,
    nombres VARCHAR(100) NOT NULL,
    apellidos VARCHAR(100) NOT NULL,
    cedula VARCHAR(30) NOT NULL UNIQUE,
    correo VARCHAR(150),
    telefono VARCHAR(20),
    cargo VARCHAR(100),
    departamento VARCHAR(100) NOT NULL,
    salario NUMERIC(12, 2) NOT NULL CHECK (salario >= 0),
    fecha_contratacion DATE NOT NULL,
    estado VARCHAR(10) NOT NULL
        CHECK (estado IN ('Activo', 'Inactivo'))
);

SELECT * FROM empleado;

INSERT INTO empleado (
    nombres, apellidos, cedula, correo, telefono,
    cargo, departamento, salario, fecha_contratacion, estado
)
VALUES
('Jesser', 'Rodriguez', '001-123456-1000A', 'jesserjrch@gmail.com', '76867526',
 'Programador', 'Tecnologia', 25000.00, '2024-01-15', 'Activo'),

('Jimmy', 'Selva', '001-123456-1000B', 'jlselvag15@gmail.com', '75184982',
 'Programador', 'Tecnología', 25000.00, '2023-06-01', 'Activo'),

('María', 'López', '001-123456-1000C', 'maria@gmail.com', '79224753',
 'Analista', 'Recursos Humanos', 16000.00, '2024-03-20', 'Activo'),

('Carlos', 'Pérez', '001-123456-1000D', 'carlos@gmail.com', '84671684',
 'Vendedor', 'Ventas', 14000.00, '2022-11-10', 'Inactivo'),

('Sofía', 'Ramírez', '001-123456-1000E', 'sofia@gmail.com', '84795285',
 'Soporte técnico', 'Tecnología', 17000.00, '2025-02-05', 'Activo'),

('José', 'Hernández', '001-123456-1000F', 'jose@gmail.com', '84597266',
 'Asistente contable', 'Finanzas', 12500.00, '2024-08-12', 'Activo'),

('Daniela', 'Torres', '001-123456-1000G', 'daniela@gmail.com', '87954107',
 'Reclutadora', 'Recursos Humanos', 15500.00, '2023-09-18', 'Inactivo'),

('Miguel', 'Castro', '001-123456-1000H', 'miguel@gmail.com', '84975308',
 'Supervisor', 'Ventas', 22000.00, '2022-04-25', 'Activo'),

('Valeria', 'Morales', '001-123456-1000I', 'valeria@gmail.com', '84870529',
 'Administradora', 'Administración', 20000.00, '2024-05-06', 'Activo'),

('Pedro', 'Mendoza', '001-123456-1000J', 'pedro@gmail.com', '84973040',
 'Asistente administrativo', 'Administración', 13000.00,
 '2025-01-13', 'Inactivo');

SELECT * FROM empleado ORDER BY id;

SELECT COUNT(*) AS total_empleados
FROM empleado;


SELECT * FROM empleado;


SELECT nombres, apellidos, cargo
FROM empleado;


SELECT * FROM empleado
WHERE departamento = 'Tecnología';


SELECT * FROM empleado
WHERE salario > 18000;


SELECT * FROM empleado
ORDER BY salario DESC;


SELECT COUNT(*) AS total_empleados
FROM empleado;


SELECT ROUND(AVG(salario), 2) AS salario_promedio
FROM empleado;

SELECT SUM(salario) AS suma_salarios
FROM empleado;

SELECT * FROM empleado
WHERE estado = 'Activo';


SELECT departamento, COUNT(*) AS cantidad
FROM empleado
GROUP BY departamento
ORDER BY departamento;

