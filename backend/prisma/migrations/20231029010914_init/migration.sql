-- CreateTable
CREATE TABLE `User` (
    `id` INTEGER NOT NULL AUTO_INCREMENT,
    `avatar` VARCHAR(255) NOT NULL,
    `biography` VARCHAR(255) NOT NULL,
    `reliabilityScore` INTEGER NOT NULL,
    `premiumStatus` BOOLEAN NOT NULL DEFAULT false,
    `createdAt` DATETIME(3) NOT NULL DEFAULT CURRENT_TIMESTAMP(3),
    `updatedAt` DATETIME(3) NOT NULL,
    `isActive` BOOLEAN NOT NULL DEFAULT true,
    `isDeleted` BOOLEAN NOT NULL DEFAULT false,

    PRIMARY KEY (`id`)
) DEFAULT CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci;

-- CreateTable
CREATE TABLE `Image` (
    `id` INTEGER NOT NULL AUTO_INCREMENT,
    `url` VARCHAR(191) NOT NULL,
    `poiId` INTEGER NOT NULL,

    PRIMARY KEY (`id`)
) DEFAULT CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci;

-- CreateTable
CREATE TABLE `poi` (
    `id` INTEGER NOT NULL AUTO_INCREMENT,
    `latitude` VARCHAR(31) NOT NULL,
    `longitudes` VARCHAR(31) NOT NULL,
    `category` VARCHAR(255) NOT NULL,
    `status` ENUM('unlisted', 'verfied', 'private') NOT NULL,
    `description` VARCHAR(255) NOT NULL,
    `ownerId` INTEGER NOT NULL,
    `rating` INTEGER NOT NULL,
    `isDeleted` BOOLEAN NOT NULL DEFAULT false,

    PRIMARY KEY (`id`)
) DEFAULT CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci;

CREATE TABLE `Review` (
    `id` INTEGER NOT NULL AUTO_INCREMENT,
    `userId` INTEGER NOT NULL
    `poiId` INTEGER NOT NULL
    `rating` INTEGER NOT NULL,
    `description` VARCHAR(255) NOT NULL,
    `reliabilityScore` INTEGER NOT NULL,
    `createdAt` DATETIME(3) NOT NULL DEFAULT CURRENT_TIMESTAMP(3),
    `updatedAt` DATETIME(3) NOT NULL,
    `isDeleted` BOOLEAN NOT NULL DEFAULT false,

    PRIMARY KEY (`id`)
) DEFAULT CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci;

CREATE TABLE `Friendship` (
    `friendshipId` INTEGER NOT NULL AUTO_INCREMENT,
    `userIdFrom` INTEGER NOT NULL,
    `userIdTo` INTEGER NOT NULL,
    `status` ENUM('requested', 'accepted', 'rejected') NOT NULL,
    `createdAt` DATETIME(3) NOT NULL DEFAULT CURRENT_TIMESTAMP(3),

    PRIMARY KEY (`friendshipId`)
) DEFAULT CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci;

-- AddForeignKey
ALTER TABLE `Image` ADD CONSTRAINT `Image_poiId_fkey` FOREIGN KEY (`poiId`) REFERENCES `poi`(`id`) ON DELETE RESTRICT ON UPDATE CASCADE;

-- AddForeignKey
ALTER TABLE `poi` ADD CONSTRAINT `poi_ownerId_fkey` FOREIGN KEY (`ownerId`) REFERENCES `User`(`id`) ON DELETE RESTRICT ON UPDATE CASCADE;

-- AddForeignKey
ALTER TABLE `Review` ADD CONSTRAINT `Review_userId_fkey` FOREIGN KEY (`userId`) REFERENCES `User`(`id`) ON DELETE RESTRICT ON UPDATE CASCADE;
ALTER TABLE `Review` ADD CONSTRAINT `Review_poiId_fkey` FOREIGN KEY (`poiId`) REFERENCES `poi`(`id`) ON DELETE RESTRICT ON UPDATE CASCADE;
