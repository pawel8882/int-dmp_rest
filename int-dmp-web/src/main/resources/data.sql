
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

  INSERT INTO persons_projects (project_id, person_id, adding_date) VALUES

  (1, 1, 'dzisiaj'),
  (2, 1, 'wczoraj'),
  (3, 1, 'jutro'),
  (1, 2, 'nigdy'),
  (2, 4, 'mały jeż'),
  (3, 4, 'lubieje'),
  (1, 5, 'przedwczoraj'),
  (4, 3, 'za dwa dni');

   INSERT INTO department (project_id, name, description) VALUES

   (1,'Architektura', 'Krajobraz'),
   (1,'Instalacje', 'wentylacja, tryskaczowa, elektryczna'),
   (1,'Konstrukcja', 'stalowa, żelbetowa'),
   (1,'Krawężnik', 'z betonu'),
   (2,'Architektura', 'Krajobraz'),
   (2,'Instalacje', 'wentylacja, tryskaczowa, elektryczna'),
   (2,'Konstrukcja', 'stalowa, żelbetowa'),
   (3,'Krawężnik', 'z betonu'),
   (3,'Architektura', 'Krajobraz'),
   (3,'Instalacje', 'wentylacja, tryskaczowa, elektryczna'),
   (3,'Konstrukcja', 'stalowa, żelbetowa'),
   (3,'Fundamenty', 'z betonu'),
   (4,'Fundamenty', 'Krajobraz');

INSERT INTO menu (department_id, label, icon, placement) VALUES

(1,'Pliki', 'pi pi-folder', 1),
(1,'Przypięte wiadomości', 'pi pi-fw pi-pencil', 2),
(1,'Zadania', 'pi pi-fw pi-question', 3),
(1,'Inne', 'pi pi-fw pi-cog', 4),
(2,'Pliki', 'pi pi-folder', 1),
(2,'Przypięte wiadomości różne', 'pi pi-fw pi-pencil', 2),
(2,'Zadania', 'pi pi-fw pi-question', 3),
(2,'Inne', 'pi pi-fw pi-cog', 4),
(3,'Pliki', 'pi pi-folder', 1),
(3,'Przypięte wiadomości', 'pi pi-fw pi-pencil', 2),
(3,'Zadania i zadanka', 'pi pi-fw pi-question', 3),
(3,'Inne', 'pi pi-fw pi-cog', 4),
(4,'Pliki', 'pi pi-folder', 1),
(4,'Przypięte wiadomości', 'pi pi-fw pi-pencil', 2),
(4,'Zadania', 'pi pi-fw pi-question', 3),
(4,'Różne', 'pi pi-fw pi-cog', 4);

INSERT INTO menu_level1 (menu_id, label, icon, placement) VALUES

(1,'Nowy', 'pi pi-fw pi-plus', 1),
(1,'Otwórz', 'pi pi-fw pi-external-link', 2),
(1,'Wyjdź', 'pi pi-fw pi-times', 3);

INSERT INTO menu_level2 (menu_level1_id, label, icon, placement) VALUES

(1,'Folder', 'pi pi-fw pi-user-plus', 1),
(1,'Plik', 'pi pi-fw pi-filter', 2);

INSERT INTO header (title, concerns) VALUES

('Folder', 'test1'),
('Plik', 'test2');