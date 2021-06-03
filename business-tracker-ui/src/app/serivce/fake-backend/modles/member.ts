export class Member {
  id: number;
  projectId: number;
  roadMapId: number;

  img: string;
  name: string;
  lastName: string;
  position: string;

  role: 'founder' | 'mentor' | 'investor' | 'manager';
}
