
insert into tarjetas (numero,cvc,titular,fecha_caducidad,saldo,uuid)
    values
        ('1234567890123456', '123', 'Juan Perez', '2025-12-31', 1000.00,UUID()),
        ('9876543210987654', '456', 'Maria Gomez', '2024-11-30', 500.00, UUID()),
        ('5555666677778888', '789', 'Carlos Sanchez', '2026-01-15', 750.00, UUID()),
        ('4444333322221111', '321', 'Ana Martinez', '2023-10-20', 300.00, UUID()),
        ('1111222233334444', '654', 'Luis Rodriguez', '2025-05-25', 1200.00, UUID());

select