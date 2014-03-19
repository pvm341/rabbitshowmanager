-- Select Colours for a breed 
SELECT colours.colour FROM public.breedcolours, public.breeds, public.colours WHERE breedcolours.breed_id = breeds.id AND breedcolours.colour_id = colours.id AND breeds.id = ?;
