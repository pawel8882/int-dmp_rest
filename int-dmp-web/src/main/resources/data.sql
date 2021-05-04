
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

   INSERT INTO section_departments (section, description, project_id, code) VALUES

   ('Architektura', 'Krajobraz', 1, 'ARCH'),
   ('Konstrukcja stalowa', 'Płatwie, dźwigary', 1, 'KOST'),
   ('Konstrukcja żelbetowa', 'słupy, podciągi', 1, 'KOZB'),
   ('Instalacje', 'elektryczne, wetnylacja', 1, 'INST');

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


   INSERT INTO department (name, description,  section_department_id, code) VALUES

   ('Hala', 'Krajobraz', 1, 'KRAJ'),
   ('Budynek biurowy', 'wentylacja, tryskaczowa, elektryczna', 1, 'TRYS'),
   ('Płatwie', 'stalowa, żelbetowa', 2, 'PLST'),
   ('Podciągi', 'z betonu', 2, 'PDZB'),
   ('Blacha trapezowa', 'Krajobraz', 2, 'BLTR'),
   ('Sciany żelbetowe', 'wentylacja, tryskaczowa, elektryczna', 3, 'SCZZ'),
   ('Słupy', 'stalowa, żelbetowa', 3, 'SSZE'),
   ('Podciągi', 'z betonu', 3, 'PDZB'),
   ('Wentylacja', 'Krajobraz', 4, 'WETK'),
   ('Elektryka', 'wentylacja, tryskaczowa, elektryczna', 4, 'ELEK');



INSERT INTO Categories_Messages (person_id, category, color) VALUES

    (1, 'Ogólny' , 'red'),
    (1, 'Architektura' , 'red'),
    (2, 'Ogólny' , 'red'),
    (3, 'Ogólny' , 'red'),
    (4, 'Ogólny' , 'red'),
    (5, 'Ogólny' , 'red');





