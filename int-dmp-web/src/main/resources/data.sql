
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

   INSERT INTO section_departments (section, description, project_id) VALUES

   ('Architektura', 'Krajobraz', 1),
   ('Konstrukcja stalowa', 'Płatwie, dźwigary', 1),
   ('Konstrukcja żelbetowa', 'słupy, podciągi', 1),
   ('Instalacje', 'elektryczne, wetnylacja', 1);

     INSERT INTO Person (first_name, last_name, role, username) VALUES
        ('Jan', 'Jan','inżynier', 'janko'),
        ('Ryszard', 'Rynk','Rysunki' , 'leszcz'),
        ('Herr', 'Schmidt','Obliczenia' , 'krauzer'),
        ('Maria', 'Rod','Manager', 'baba'),
        ('Judyta', 'Ogórek','Pasterz' , 'babsko');

INSERT INTO section_departments_persons (person_id, section_department_id) VALUES

   (1, 3),
   (1, 2),
   (2, 3),
   (3, 2),
   (4, 1),
   (5, 1);

  INSERT INTO persons_projects (project_id, person_id) VALUES

  (1, 1),
  (2, 1),
  (3, 1),
  (1, 2),
  (2, 4),
  (3, 4),
  (1, 5),
  (2, 5),
  (4, 3);


   INSERT INTO department (name, description,  section_department_id) VALUES

   ('Hala', 'Krajobraz', 1),
   ('Budynek biurowy', 'wentylacja, tryskaczowa, elektryczna', 1),
   ('Płatwie', 'stalowa, żelbetowa', 2),
   ('Podciągi', 'z betonu', 2),
   ('Blacha trapezowa', 'Krajobraz', 2),
   ('Sciany żelbetowe', 'wentylacja, tryskaczowa, elektryczna', 3),
   ('Słupy', 'stalowa, żelbetowa', 3),
   ('Podciągi', 'z betonu', 3),
   ('Wentylacja', 'Krajobraz', 4),
   ('Elektryka', 'wentylacja, tryskaczowa, elektryczna', 4);



INSERT INTO Categories_Messages (person_id, category, color) VALUES

    (1, 'Ogólny' , 'red'),
    (1, 'Architektura' , 'red'),
    (2, 'Ogólny' , 'red'),
    (3, 'Ogólny' , 'red'),
    (4, 'Ogólny' , 'red'),
    (5, 'Ogólny' , 'red');





