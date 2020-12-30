
INSERT INTO Project (name, complete, number) VALUES
  ('Hala stalowa', false, '885'),
  ('Hala żelbetowa', false, '999'),
  ('Placek', false, '547'),
  ('Naleśnik', true, '257');

  INSERT INTO Project_Details (project_id, description) VALUES
    (1,'Super hala stalowa'),
    (2,'Super hala żelbetowa'),
    (3,'z nadzieniem czekoladowym'),
    (4,'zawijany w super rulon makowy');

     INSERT INTO Person (first_name, last_name, role) VALUES
        ('Maciek', 'z klanu','inżynier'),
        ('Jeży', 'Jeżowski','Rysunki'),
        ('Marian', 'Mediamarkt','Obliczenia'),
        ('Zofia', 'Tak','Manager'),
        ('Ryszard', 'z na dobre i na złe','Pasterz');

  INSERT INTO persons_projects (project_id, person_id) VALUES

  (1, 1),
  (2, 1),
  (3, 1),
  (1, 2),
  (2, 4),
  (3, 4),
  (1, 5);