import {Task} from './task';
import {Sprint} from './sprint';
import {Kpi} from './kpi';
import {Resource} from './resource';
import {RoadMap} from './road-map';
import {Member} from './member';
import * as faker from 'faker';
import {Delivery} from './delivery';

export const roadMapsSource: RoadMap[] = generateRoadMap();

function generateRoadMap(): RoadMap[] {

  const roadMap1: RoadMap = new RoadMap();

  roadMap1.name = 'Product Roadmap';
  roadMap1.members = generateMembers(roadMap1.id);
  roadMap1.tasks = generateTasks(roadMap1.id);

  function generateTasks(roadMapId: number): Task [] {
    const task1: Task = new Task();
    const task2: Task = new Task();
    const task3: Task = new Task();

    task1.name = 'Research';
    task2.name = 'Prototyping';
    task3.name = 'Market-fit';

    task1.startDate = new Date(2021, 5, 1);
    task2.startDate = plusTwoWeeks(task1.startDate);
    task3.startDate = plusTwoWeeks(task2.startDate);

    task1.finishDate = task2.startDate;
    task2.finishDate = task3.startDate;
    task3.finishDate = plusTwoWeeks(task3.startDate);

    function plusTwoWeeks(startDate: Date): Date {
      return new Date(startDate.getFullYear(), startDate.getMonth(), startDate.getDate() + 14);
    }

    task1.statusBarValue = getStatusBar(task1);
    task2.statusBarValue = getStatusBar(task2);
    task3.statusBarValue = getStatusBar(task3);

    function getStatusBar(task: Task): number {
      const startDate = task.startDate.getTime();
      const endDate = task.finishDate.getTime();
      const now = new Date().getTime();

      const q = now - startDate;
      const d = endDate - startDate;
      return faker.datatype.number({min: 4, max: 100});
    }

    task1.roadMapId = roadMapId;
    task2.roadMapId = roadMapId;
    task3.roadMapId = roadMapId;

    task1.kpis = generateKpi(task1.id);
    task2.kpis = generateKpi(task2.id);
    task3.kpis = generateKpi(task3.id);

    task1.sprints = generateSprint(task1.id, roadMap1.members);
    task2.sprints = generateSprint(task2.id, roadMap1.members);
    task3.sprints = generateSprint(task3.id, roadMap1.members);

    return [task1, task2, task3];
  }

  return [roadMap1];
}

function generateSprint(id: number, members: Member[]): Sprint[] {
  const returnValue: Sprint[] = [];

  const counter = faker.datatype.number({min: 6, max: 12});

  function generateDelivers(sprintId: number): Delivery[] {
    const delivers: Delivery[] = [];
    for (let i = 0; i < 3; i++) {
      const deliver: Delivery = new Delivery();
      deliver.name = faker.random.word('information');
      deliver.sprintId = sprintId;
      delivers.push(deliver);
    }
    return delivers;
  }

  for (let i = 0; i < counter; i++) {
    const sprint: Sprint = new Sprint();
    sprint.active = id === 2 && i < 4;
    sprint.finished = id === 1;
    sprint.name = faker.lorem.sentence(5, 60);

    const responsibleMembers: Set<Member> = new Set<Member>();
    for (let j = 0; j < members.length / 3; j++) {
      const memberIndex = faker.datatype.number({min: 0, max: members.length - 1});
      responsibleMembers.add(members[memberIndex]);
    }
    sprint.responsibleMembers = Array.from(responsibleMembers);
    sprint.resources = generateResource(sprint.id);
    sprint.delivers = generateDelivers(sprint.id);

    returnValue.push(sprint);
  }
  return returnValue;
}

function generateMembers(id: number): Member[] {
  const returnValue: Member[] = [];

  const counter = faker.datatype.number({min: 4, max: 12});
  for (let i = 0; i < counter; i++) {
    const member: Member = new Member();
    member.name = faker.name.firstName(i);
    member.lastName = faker.name.lastName(i);
    member.img = faker.image.avatar();

    member.position = faker.name.jobTitle();
    member.role = 'founder';
    returnValue.push(member);
  }
  return returnValue;
}

function generateKpi(id: number): Kpi[] {
  const returnValue: Kpi[] = [];

  const counter = faker.datatype.number({min: 3, max: 10});
  for (let i = 0; i < counter; i++) {
    const kpi: Kpi = new Kpi();
    kpi.taskId = id;
    kpi.name = faker.lorem.sentence(6, 860);
    returnValue.push(kpi);
  }
  return returnValue;
}

function generateResource(id: number): Resource[] {
  const returnValue: Resource[] = [];

  const counter = faker.datatype.number({min: 3, max: 10});
  for (let i = 0; i < counter; i++) {
    const resource: Resource = new Resource();
    resource.sprintId = id;
    resource.name = faker.name.firstName(i);
    resource.cost = faker.datatype.number({min: 550, max: 1500});
    resource.time = faker.datatype.number({min: 10, max: 16});
    returnValue.push(resource);
  }
  return returnValue;
}
