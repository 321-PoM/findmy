import { PrismaClient } from '@prisma/client'
import { mockDeep, mockReset, DeepMockProxy } from 'jest-mock-extended'
import { jest, beforeEach} from '@jest/globals'

import testprisma from './client'

jest.mock('./prisma/client', () => ({
    __esModule: true,
    default: mockDeep<PrismaClient>(),
}));

beforeEach(() => {
    mockReset(prismaMock)
});

export const prismaMock = testprisma as unknown as DeepMockProxy<PrismaClient>;