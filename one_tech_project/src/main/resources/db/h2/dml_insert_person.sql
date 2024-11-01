-- 1. Вставка пользователя и получение его ID
INSERT INTO persons (username, email, password)
VALUES ('user', 'user@gmail.com', '123456789')
RETURNING id;  -- Предположим, что возвращаемый ID - это 1

-- 2. Присвоение ролей (предположим, ID пользователя 1 и роли 1 и 2 существуют)
INSERT INTO persons_roles (person_id, role_id)
VALUES (1, 1), (1, 2);
